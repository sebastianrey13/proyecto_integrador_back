package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.FavoriteDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Favorite;
import com.grupo5.AlquilerEquiposConstruccion.repository.FavoriteRepository;
import com.grupo5.AlquilerEquiposConstruccion.service.FavoriteService;
import jakarta.transaction.Transactional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    FavoriteRepository favoriteRepository;

    @Autowired
    private ObjectMapper mapper;

    private final Logger logger = Logger.getLogger(CityServiceImpl.class);

    @Override
    public List<FavoriteDTO> getAllFavorites() {
        List<Favorite> favorites = favoriteRepository.findAll();
        List<FavoriteDTO> favoritesDTO = new ArrayList<>();
        for(Favorite fav : favorites){
            favoritesDTO.add(mapper.convertValue(fav, FavoriteDTO.class));
        }
        return favoritesDTO;
    }

    @Override
    public Optional<FavoriteDTO> getFavoriteById(Integer id) throws NotFoundException {
        Favorite favoriteFounded = favoriteRepository.findById(id).orElseThrow(() -> new NotFoundException("The " +
                "favorite with the id: " + id + " was not found."));
        return Optional.ofNullable(mapper.convertValue(favoriteFounded, FavoriteDTO.class));
    }

    @Override
    public FavoriteDTO createFavorite(FavoriteDTO favorite) throws BadRequestException {
        if (favorite.getUser()==null || favorite.getProduct()==null){
            throw new BadRequestException("The favorite has null values.");
        } else{
            Favorite favoriteCreated = mapper.convertValue(favorite, Favorite.class);
            logger.info("The favorite was created successfully.");
            return mapper.convertValue(favoriteRepository.save(favoriteCreated), FavoriteDTO.class);
        }
    }

    @Override
    public FavoriteDTO updateFavorite(FavoriteDTO favorite) throws NotFoundException {
        Integer id = favorite.getId();
        Optional<FavoriteDTO> existingFavorite = getFavoriteById(id);
        if (existingFavorite.isPresent()) {
            FavoriteDTO existingFavoriteDTO = existingFavorite.get();
            existingFavoriteDTO.setProduct(favorite.getProduct());
            existingFavoriteDTO.setUser(favorite.getUser());
            Favorite favoriteUpdated = mapper.convertValue(existingFavoriteDTO, Favorite.class);
            favoriteRepository.save(favoriteUpdated);
            logger.info("The favorite was updated successfully.");
        }
        return existingFavorite.orElseThrow(() -> new NotFoundException("Favorite not found"));
    }


    @Override
    public void deleteFavoriteById(Integer id) throws NotFoundException {
        favoriteRepository.findById(id).orElseThrow(() -> new NotFoundException("The " +
                "favorite with the id: " + id + " was not found."));
        favoriteRepository.deleteById(id);
    }

    @Override
    public List<FavoriteDTO> findByProduct_id(Integer id) throws NotFoundException {
        List<Favorite> favoriteFounded = favoriteRepository.findByProduct_id(id);
        return favoriteFounded.stream()
                .map(favorite -> mapper.convertValue(favorite, FavoriteDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FavoriteDTO> findByUser_id(Integer id) throws NotFoundException {
        List<Favorite> favoriteFounded = favoriteRepository.findByUser_id(id);
        return favoriteFounded.stream()
                .map(favorite -> mapper.convertValue(favorite, FavoriteDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FavoriteDTO> findByUser_idAndProduct_id(Integer userId, Integer productId) throws NotFoundException {
        Favorite favoriteFounded = favoriteRepository.findByUser_idAndProduct_id(userId, productId).orElseThrow(() -> new NotFoundException("false"));
        return Optional.ofNullable(mapper.convertValue(favoriteFounded, FavoriteDTO.class));
    }

    @Override
    @Transactional
    public void deleteByUser_idAndProduct_id(Integer userId, Integer productId) throws NotFoundException {
        favoriteRepository.findByUser_idAndProduct_id(userId, productId).orElseThrow(() -> new NotFoundException("The " +
                "favorite for the user with the id " + userId + " and the product with the id " + productId + " was not found."));
        favoriteRepository.deleteByUser_idAndProduct_id(userId, productId);
    }
}
