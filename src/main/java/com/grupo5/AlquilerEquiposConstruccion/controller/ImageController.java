package com.grupo5.AlquilerEquiposConstruccion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.ImageDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.service.ImageService;
import com.grupo5.AlquilerEquiposConstruccion.service.ProductService;
import com.grupo5.AlquilerEquiposConstruccion.service.S3Service;
import com.grupo5.AlquilerEquiposConstruccion.utils.FileManager;
import com.grupo5.AlquilerEquiposConstruccion.utils.S3Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

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
    public ResponseEntity<List<ImageDTO>> getAllImages() throws NotFoundException {
        return ResponseEntity.ok(imageService.getAllImages());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<ImageDTO>> findByproduct_id(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok(imageService.findByproduct_id(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageDTO> getImageById(@PathVariable Integer id) throws NotFoundException {
        Optional<ImageDTO> imageSearch = imageService.getImageById(id);
        if (imageSearch.isPresent()) {
            return ResponseEntity.ok(imageSearch.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<?> saveImageByProductId(@RequestParam("file") MultipartFile[] file, @PathVariable Integer id) throws IOException, NotFoundException, BadRequestException {

        Map<String, String> imagesData = new HashMap<>();

        for (MultipartFile f: file) {
            File convertedFile = fileManager.convertMultiPartFileToFile(f);
            String fileName = fileManager.generateFileName(f);
            s3Service.uploadFile(fileName, convertedFile);
            convertedFile.delete();
            String s3Url = s3Config.amazonS3().getUrl(bucketName, fileName).toString();
            imagesData.put(fileName, s3Url);
        }

        for (Map.Entry<String, String> entry : imagesData.entrySet()) {
            String name = entry.getKey();
            String url = entry.getValue();
            ImageDTO imageDTO = new ImageDTO(name, url);
            imageService.saveImageByProductId(imageDTO, id);
        }

        List<ImageDTO> images = imageService.findByproduct_id(id);

        return ResponseEntity.ok(images);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateImage(@RequestBody ImageDTO imageDTO) throws Exception{
        Optional<ImageDTO> imageSearch = imageService.getImageById(imageDTO.getId());
        if (imageSearch.isPresent()) {
            return ResponseEntity.ok(imageService.updateImage(imageDTO, imageSearch.get().getId()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image with ID: " + imageDTO.getId() + " was not found.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> eliminarImagen(@PathVariable Integer id) throws NotFoundException {
        if (imageService.getImageById(id).isPresent()) {
            String imageKey = imageService.getImageById(id).get().getTitle();
            s3Service.deleteFile(imageKey);
            imageService.deleteImageById(id);
            return ResponseEntity.ok("The image with id: " + id + " was deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image with id: " + id + " was not found.");
    }

    @DeleteMapping("/delete/all/{id}")
    public ResponseEntity<String> deleteAllByProduct_id(@PathVariable Integer id) throws NotFoundException {
        if (productService.getProductById(id).isPresent()) {
            imageService.deleteByProduct_id(id);
            return ResponseEntity.ok("The images of the product with id: " + id + " were successfully deleted.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id: " + id + " was not found.");
    }

}
