package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.ImageDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.ProductDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Image;
import com.grupo5.AlquilerEquiposConstruccion.model.Product;
import com.grupo5.AlquilerEquiposConstruccion.repository.ImageRepository;
import com.grupo5.AlquilerEquiposConstruccion.service.ImageService;
import com.grupo5.AlquilerEquiposConstruccion.service.ProductService;
import jakarta.transaction.Transactional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private final Logger logger = Logger.getLogger(CityServiceImpl.class);

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    ObjectMapper mapper;

    @Override
    public List<ImageDTO> getAllImages() {
        List<Image> images = imageRepository.findAll();
        List<ImageDTO> imagesDTO = new ArrayList<>();
        for(Image image : images){
            imagesDTO.add(mapper.convertValue(image, ImageDTO.class));
        }
        return imagesDTO;
    }

    @Override
    public List<ImageDTO> findByproduct_id(Integer id) {
        List<Image> images = imageRepository.findByproduct_id(id);
        List<ImageDTO> imagesDTO = new ArrayList<>();
        for(Image image : images){
            imagesDTO.add(mapper.convertValue(image, ImageDTO.class));
        }
        return imagesDTO;
    }

    @Override
    public Optional<ImageDTO> getImageById(Integer id) throws NotFoundException {
        Image imageFounded = imageRepository.findById(id).orElseThrow(() -> new NotFoundException("The " +
                "image with the id: " + id + " was not found."));
        return Optional.ofNullable(mapper.convertValue(imageFounded, ImageDTO.class));
    }

    @Override
    public ImageDTO saveImage(ImageDTO imageDTO) throws BadRequestException {
        if (imageDTO.getUrl()==null){
            throw new BadRequestException("The image has null values.");
        } else{
            Image imageCreated = mapper.convertValue(imageDTO, Image.class);
            logger.info("The image was created successfully.");
            return mapper.convertValue(imageRepository.save(imageCreated), ImageDTO.class);
        }
    }
    @Override
    public ImageDTO saveImageByProductId(ImageDTO imageDTO, Integer id) throws BadRequestException, NotFoundException {
        if (imageDTO.getUrl()==null){
            throw new BadRequestException("The image has null values.");
        } else{
            Image imageCreated = mapper.convertValue(imageDTO, Image.class);
            Optional<ProductDTO> productDTO = productService.getProductById(id);
            Product productEntity = mapper.convertValue(productDTO.get(), Product.class);
            imageCreated.setProduct(productEntity);
            logger.info("The image was created successfully.");
            return mapper.convertValue(imageRepository.save(imageCreated), ImageDTO.class);
        }
    }

    @Override
    public ImageDTO updateImage(ImageDTO imageDTO, Integer id) throws NotFoundException {
        Optional<ImageDTO> existingImage = getImageById(id);
        if (existingImage.isPresent()){
            ImageDTO existingImageDTO = existingImage.get();
            existingImageDTO.setUrl(imageDTO.getUrl());

            Image imageUpdated = mapper.convertValue(existingImageDTO, Image.class);
            imageRepository.save(imageUpdated);

            logger.info("The image was updated successfully.");
        }
        return existingImage.orElse(null);
    }


    @Override
    public void deleteImageById(Integer id) throws NotFoundException {
        imageRepository.findById(id).orElseThrow(() -> new NotFoundException("The " +
                "image with the id: " + id + " was not found."));
        imageRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByProduct_id(Integer id) throws NotFoundException {
        if (imageRepository.findByproduct_id(id).isEmpty()) {
            throw new NotFoundException("The " +
                    "product with the id: " + id + " was not found.");
        }
        imageRepository.deleteByProduct_id(id);
    }

}
