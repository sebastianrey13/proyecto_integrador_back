package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.CityDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.City;
import com.grupo5.AlquilerEquiposConstruccion.repository.CityRepository;
import com.grupo5.AlquilerEquiposConstruccion.service.CityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    private final Logger logger = Logger.getLogger(CityServiceImpl.class);

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<CityDTO> getAllCities() {
        List<City> cities = cityRepository.findAll();
        List<CityDTO> citiesDTO = new ArrayList<>();
        for(City city : cities){
            citiesDTO.add(mapper.convertValue(city, CityDTO.class));
        }
        return citiesDTO;
    }

    @Override
    public Optional<CityDTO> getCityById(Integer id) throws NotFoundException {
        City cityFounded = cityRepository.findById(id).orElseThrow(() -> new NotFoundException("The " +
                "city with the id: " + id + " was not found."));
        return Optional.ofNullable(mapper.convertValue(cityFounded, CityDTO.class));
    }

    @Override
    public CityDTO saveCity(CityDTO city) throws BadRequestException {
        if (city.getName()==null){
            throw new BadRequestException("The city has null values.");
        } else{
            City categoryCreated = mapper.convertValue(city, City.class);
            logger.info("The city was created successfully.");
            return mapper.convertValue(cityRepository.save(categoryCreated), CityDTO.class);
        }
    }
    @Override
    public CityDTO updateCity(CityDTO city, Integer id) throws NotFoundException {
        Optional<CityDTO> existingCity = getCityById(id);
        if (existingCity.isPresent()) {
            existingCity.get().setName(city.getName());
            City cityUpdated = mapper.convertValue(existingCity.get(), City.class);
            cityRepository.save(cityUpdated);
            logger.info("The city was updated successfully.");
        }
        return existingCity.orElseThrow(() -> new NotFoundException("City not found."));
    }


    @Override
    public void deleteCityById(Integer id) throws NotFoundException {
        cityRepository.findById(id).orElseThrow(() -> new NotFoundException("The " +
                "city with the id: " + id + " was not found."));
        cityRepository.deleteById(id);
        }
}

