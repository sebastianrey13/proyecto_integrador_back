package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.CategoryDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.CharacteristicDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.ImageDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.ProductDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Category;
import com.grupo5.AlquilerEquiposConstruccion.model.Characteristic;
import com.grupo5.AlquilerEquiposConstruccion.model.Image;
import com.grupo5.AlquilerEquiposConstruccion.model.Product;
import com.grupo5.AlquilerEquiposConstruccion.repository.CharacteristicRepository;
import com.grupo5.AlquilerEquiposConstruccion.repository.ImageRepository;
import com.grupo5.AlquilerEquiposConstruccion.service.CharacteristicService;
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
public class CharacteristicServiceImpl implements CharacteristicService {

    private final Logger logger = Logger.getLogger(CityServiceImpl.class);

    @Autowired
    private CharacteristicRepository characteristicRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    ObjectMapper mapper;

    @Override
    public List<CharacteristicDTO> getAllCharacteristics() {
        List<Characteristic> characteristics = characteristicRepository.findAll();
        List<CharacteristicDTO> characteristicsDTO = new ArrayList<>();
        for(Characteristic characteristic : characteristics){
            characteristicsDTO.add(mapper.convertValue(characteristic, CharacteristicDTO.class));
        }
        return characteristicsDTO;
    }

    @Override
    public List<CharacteristicDTO> findByproduct_id(Integer id) {
        List<Characteristic> characteristics = characteristicRepository.findByproduct_id(id);
        List<CharacteristicDTO> characteristicsDTO = new ArrayList<>();
        for(Characteristic characteristic : characteristics){
            characteristicsDTO.add(mapper.convertValue(characteristic, CharacteristicDTO.class));
        }
        return characteristicsDTO;
    }

    @Override
    public Optional<CharacteristicDTO> getCharacteristicById(Integer id) throws NotFoundException {
        Characteristic characteristicFounded = characteristicRepository.findById(id).orElseThrow(() -> new NotFoundException("The " +
                "characteristic with the id: " + id + " was not found."));
        return Optional.ofNullable(mapper.convertValue(characteristicFounded, CharacteristicDTO.class));
    }

    /*@Override
    public CharacteristicDTO saveCharacteristic(CharacteristicDTO characteristicDTO) throws BadRequestException {
        if (characteristicDTO.getIcon()==null){
            throw new BadRequestException("The characteristic has null values.");
        } else{
            Characteristic characteristicCreated = mapper.convertValue(characteristicDTO, Characteristic.class);
            logger.info("The characteristic was created successfully.");
            return mapper.convertValue(characteristicRepository.save(characteristicCreated), CharacteristicDTO.class);
        }
    }*/

    @Override
    public CharacteristicDTO saveCharacteristic(CharacteristicDTO characteristic) throws BadRequestException {
        if (characteristic.getCharacteristic()==null){
            throw new BadRequestException("The category has null values.");
        } else{
            Characteristic characteristicCreated = mapper.convertValue(characteristic, Characteristic.class);
            logger.info("The characteristic was created successfully.");
            return mapper.convertValue(characteristicRepository.save(characteristicCreated), CharacteristicDTO.class);
        }
    }


    @Override
    public CharacteristicDTO saveCharacteristicByProductId(CharacteristicDTO characteristicDTO, Integer id) throws BadRequestException, NotFoundException {
        if (characteristicDTO.getIcon()==null){
            throw new BadRequestException("The characteristic has null values.");
        } else{
            Characteristic characteristicCreated = mapper.convertValue(characteristicDTO, Characteristic.class);
            Optional<ProductDTO> productDTO = productService.getProductById(id);
            Product productEntity = mapper.convertValue(productDTO.get(), Product.class);
            characteristicCreated.setProduct(productEntity);
            logger.info("The characteristic was created successfully.");
            return mapper.convertValue(characteristicRepository.save(characteristicCreated), CharacteristicDTO.class);
        }
    }

    @Override
    public CharacteristicDTO updateCharacteristic(CharacteristicDTO characteristicDTO, Integer id) throws NotFoundException {
        Optional<CharacteristicDTO> existingCharacteristic = getCharacteristicById(id);
        if (existingCharacteristic.isPresent()){
            existingCharacteristic.get().setIcon(characteristicDTO.getIcon());
            Characteristic characteristicUpdated = mapper.convertValue(existingCharacteristic, Characteristic.class);
            characteristicRepository.save(characteristicUpdated);
            logger.info("The characteristic was updated successfully.");
        }
        return mapper.convertValue(existingCharacteristic, CharacteristicDTO.class);
    }

    @Override
    public void deleteCharacteristicById(Integer id) throws NotFoundException {
        characteristicRepository.findById(id).orElseThrow(() -> new NotFoundException("The " +
                "characteristic with the id: " + id + " was not found."));
        characteristicRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByProduct_id(Integer id) throws NotFoundException {
        if (characteristicRepository.findByproduct_id(id).isEmpty()) {
            throw new NotFoundException("The " +
                    "product with the id: " + id + " was not found.");
        }
        characteristicRepository.deleteByProduct_id(id);
    }

}
