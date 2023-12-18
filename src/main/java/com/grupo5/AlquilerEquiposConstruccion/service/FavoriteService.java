package com.grupo5.AlquilerEquiposConstruccion.service;

import com.grupo5.AlquilerEquiposConstruccion.dto.FavoriteDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface FavoriteService {
    List<FavoriteDTO> getAllFavorites();
    Optional<FavoriteDTO> getFavoriteById(Integer id) throws NotFoundException;
    FavoriteDTO createFavorite(FavoriteDTO favorite) throws BadRequestException;
    FavoriteDTO updateFavorite(FavoriteDTO favorite) throws NotFoundException;
    void deleteFavoriteById(Integer id) throws NotFoundException;
    List<FavoriteDTO> findByProduct_id(Integer id) throws NotFoundException;
    List<FavoriteDTO> findByUser_id(Integer id) throws NotFoundException;
    Optional<FavoriteDTO> findByUser_idAndProduct_id(Integer userId, Integer productId) throws NotFoundException;
    void deleteByUser_idAndProduct_id(Integer userId, Integer productId) throws NotFoundException;
}
