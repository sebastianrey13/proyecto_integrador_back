package com.grupo5.AlquilerEquiposConstruccion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.CategoryDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.CharacteristicDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.ImageDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Characteristic;
import com.grupo5.AlquilerEquiposConstruccion.service.CharacteristicService;
import com.grupo5.AlquilerEquiposConstruccion.service.ImageService;
import com.grupo5.AlquilerEquiposConstruccion.service.ProductService;
import com.grupo5.AlquilerEquiposConstruccion.service.S3Service;
import com.grupo5.AlquilerEquiposConstruccion.utils.FileManager;
import com.grupo5.AlquilerEquiposConstruccion.utils.S3Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/characteristics")
public class CharacteristicController {


    @Autowired
    private CharacteristicService characteristicService;

    @Autowired
    private ProductService productService;

    @Autowired
    private S3Service s3Service;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private FileManager fileManager;

    @Autowired
    private S3Config s3Config;

    @GetMapping
    public ResponseEntity<List<CharacteristicDTO>> getAllCharacteristics() throws NotFoundException {
        return ResponseEntity.ok(characteristicService.getAllCharacteristics());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<CharacteristicDTO>> findByproduct_id(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok(characteristicService.findByproduct_id(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacteristicDTO> getCharacteristicById(@PathVariable Integer id) throws NotFoundException {
        Optional<CharacteristicDTO> characteristicSearch = characteristicService.getCharacteristicById(id);
        if (characteristicSearch.isPresent()) {
            return ResponseEntity.ok(characteristicSearch.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /* @PostMapping("/create/{id}")
    public ResponseEntity<?> saveCharacteristicByProductId(@RequestParam("file") MultipartFile[] file, @PathVariable Integer id) throws IOException, NotFoundException, BadRequestException {

        Map<String, String> characteristicsData = new HashMap<>();

        for (MultipartFile f: file) {
            File convertedFile = fileManager.convertMultiPartFileToFile(f);
            String fileName = fileManager.generateFileName(f);
            s3Service.uploadFile(fileName, convertedFile);
            convertedFile.delete();
            String s3Url = s3Config.amazonS3().getUrl(bucketName, fileName).toString();
            characteristicsData.put(fileName, s3Url);
        }

        for (Map.Entry<String, String> entry : characteristicsData.entrySet()) {
            String name = entry.getKey();
            String url = entry.getValue();
            CharacteristicDTO characteristicDTO = new CharacteristicDTO(name,url);
            characteristicService.saveCharacteristicByProductId(characteristicDTO, id);
        }

        List<CharacteristicDTO> characteristics = characteristicService.findByproduct_id(id);

        return ResponseEntity.ok(characteristics);
    }*/

    @PostMapping(path = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE ,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<CharacteristicDTO> createCharacteristic(@RequestPart("file") MultipartFile file, @RequestPart("characteristic") CharacteristicDTO characteristic) throws BadRequestException, IOException, IOException {
        File convertedFile = fileManager.convertMultiPartFileToFile(file);
        String fileName = fileManager.generateFileName(file);
        s3Service.uploadFile(fileName, convertedFile);
        convertedFile.delete();
        String s3Url = s3Config.amazonS3().getUrl(bucketName, fileName).toString();
        characteristic.setIcon(s3Url);
        return ResponseEntity.ok(characteristicService.saveCharacteristic(characteristic));
    }

    /*@PutMapping("/update")
    public ResponseEntity<?> updateCharacteristic(@RequestBody CharacteristicDTO characteristicDTO) throws Exception{
        Optional<CharacteristicDTO> characteristicSearch = characteristicService.getCharacteristicById(characteristicDTO.getId());
        if (characteristicSearch.isPresent()) {
            return ResponseEntity.ok(characteristicService.updateCharacteristic(characteristicDTO, characteristicSearch.get().getId()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Characteristic with ID: " + characteristicDTO.getId() + " was not found.");
        }
    }*/

    @PutMapping(path = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE ,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> updateCharacteristic(@RequestParam("file") MultipartFile file, @RequestPart("characteristic") CharacteristicDTO characteristic) throws Exception{
        Optional<CharacteristicDTO> characteristicSearch = characteristicService.getCharacteristicById(characteristic.getId());
        String existentIcon = characteristicService.getCharacteristicById(characteristic.getId()).get().getIcon();
        if(characteristicSearch.isPresent() && file.isEmpty()){
            characteristic.setIcon(existentIcon);
            return ResponseEntity.ok(characteristicService.updateCharacteristic(characteristic, characteristic.getId()));
        } if (characteristicSearch.isPresent() && !file.isEmpty()){
            File convertedFile = fileManager.convertMultiPartFileToFile(file);
            String fileName = fileManager.generateFileName(file);
            s3Service.uploadFile(fileName, convertedFile);
            convertedFile.delete();
            String s3Url = s3Config.amazonS3().getUrl(bucketName, fileName).toString();
            characteristic.setIcon(s3Url);
            return ResponseEntity.ok(characteristicService.updateCharacteristic(characteristic, characteristic.getId()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Characteristic with id: " + characteristic.getId() + " was not found.");
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCharacteristic(@PathVariable Integer id) throws NotFoundException {
        if (characteristicService.getCharacteristicById(id).isPresent()) {
            String imageKey = characteristicService.getCharacteristicById(id).get().getCharacteristic();
            s3Service.deleteFile(imageKey);
            characteristicService.deleteCharacteristicById(id);
            return ResponseEntity.ok("The characteristic with id: " + id + " was deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Characteristic with id: " + id + " was not found.");
    }

    @DeleteMapping("/delete/all/{id}")
    public ResponseEntity<String> deleteAllByProduct_id(@PathVariable Integer id) throws NotFoundException {
        if (productService.getProductById(id).isPresent()) {
            characteristicService.deleteByProduct_id(id);
            return ResponseEntity.ok("The characteristics of the product with id: " + id + " were successfully deleted.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id: " + id + " was not found.");
    }

}
