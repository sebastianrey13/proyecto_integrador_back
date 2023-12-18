package com.grupo5.AlquilerEquiposConstruccion.service;


import com.grupo5.AlquilerEquiposConstruccion.dto.CharacteristicDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface CharacteristicService {

    List<CharacteristicDTO> getAllCharacteristics();
    List<CharacteristicDTO> findByproduct_id(Integer id);
    Optional<CharacteristicDTO> getCharacteristicById(Integer id) throws NotFoundException;
    CharacteristicDTO saveCharacteristic(CharacteristicDTO characteristicDTO) throws BadRequestException;
    CharacteristicDTO saveCharacteristicByProductId(CharacteristicDTO characteristicDTO, Integer id) throws BadRequestException, NotFoundException;
    CharacteristicDTO updateCharacteristic(CharacteristicDTO characteristicDTO, Integer id) throws NotFoundException;
    void deleteCharacteristicById(Integer id) throws NotFoundException;
    void deleteByProduct_id(Integer id) throws NotFoundException;
}
