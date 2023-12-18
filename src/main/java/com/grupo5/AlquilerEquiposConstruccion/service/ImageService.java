package com.grupo5.AlquilerEquiposConstruccion.service;

import com.grupo5.AlquilerEquiposConstruccion.dto.ImageDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    List<ImageDTO> getAllImages();
    List<ImageDTO> findByproduct_id(Integer id);
    Optional<ImageDTO> getImageById(Integer id) throws NotFoundException;
    ImageDTO saveImage(ImageDTO imageDTO) throws BadRequestException;
    ImageDTO saveImageByProductId(ImageDTO imageDTO, Integer id) throws BadRequestException, NotFoundException;
    ImageDTO updateImage(ImageDTO imageDTO, Integer id) throws NotFoundException;
    void deleteImageById(Integer id) throws NotFoundException;
    void deleteByProduct_id(Integer id) throws NotFoundException;
}
