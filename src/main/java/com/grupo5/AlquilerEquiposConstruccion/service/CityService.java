package com.grupo5.AlquilerEquiposConstruccion.service;

import com.grupo5.AlquilerEquiposConstruccion.dto.CityDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface CityService {
    List<CityDTO> getAllCities();
    Optional<CityDTO> getCityById(Integer id) throws NotFoundException;
    CityDTO saveCity(CityDTO city) throws BadRequestException;
    CityDTO updateCity(CityDTO city, Integer id) throws NotFoundException;
    void deleteCityById(Integer id) throws NotFoundException;
}
