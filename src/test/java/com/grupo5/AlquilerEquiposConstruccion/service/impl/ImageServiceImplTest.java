package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.ImageDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Image;
import com.grupo5.AlquilerEquiposConstruccion.repository.ImageRepository;
import com.grupo5.AlquilerEquiposConstruccion.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class ImageServiceImplTest {

    @InjectMocks
    private ImageServiceImpl imageService;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private ProductService productService;

    @Mock
    private ObjectMapper objectMapper;

    private ImageDTO sampleImageDTO;
    private Image sampleImage;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleImageDTO = new ImageDTO("image title", "Sample Image URL");
        sampleImage = new Image(1,"image title", "Sample Image URL");

        when(objectMapper.convertValue(eq(sampleImageDTO), eq(Image.class)))
                .thenReturn(sampleImage);
        when(objectMapper.convertValue(eq(sampleImage), eq(ImageDTO.class)))
                .thenReturn(sampleImageDTO);
    }

    @Test
    public void testGetAllImages() {
        // Arrange
        when(imageRepository.findAll()).thenReturn(List.of(sampleImage));

        // Act
        List<ImageDTO> result = imageService.getAllImages();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(sampleImageDTO.getId(), result.get(0).getId());
    }

    @Test
    public void testGetImageById() throws NotFoundException {
        // Arrange
        when(imageRepository.findById(sampleImageDTO.getId())).thenReturn(Optional.of(sampleImage));

        // Act
        Optional<ImageDTO> result = imageService.getImageById(sampleImageDTO.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(sampleImageDTO.getId(), result.get().getId());
    }

    @Test
    public void testSaveImage() throws BadRequestException {
        // Arrange
        when(imageRepository.save(Mockito.any(Image.class))).thenReturn(sampleImage);

        // Act
        ImageDTO result = imageService.saveImage(sampleImageDTO);

        // Assert
        assertNotNull(result);
        assertEquals(sampleImageDTO.getId(), result.getId());
        assertEquals(sampleImageDTO.getUrl(), result.getUrl());
    }

    @Test
    public void testUpdateImage() throws NotFoundException {
        // Arrange
        when(imageRepository.findById(sampleImageDTO.getId())).thenReturn(Optional.of(sampleImage));
        when(imageRepository.save(Mockito.any(Image.class))).thenReturn(sampleImage);

        // Act
        ImageDTO result = imageService.updateImage(sampleImageDTO, sampleImageDTO.getId());

        // Assert
        assertNotNull(result);
        assertEquals(sampleImageDTO.getId(), result.getId());
        assertEquals(sampleImageDTO.getUrl(), result.getUrl());

        verify(imageRepository, times(1)).findById(sampleImageDTO.getId());
        verify(imageRepository, times(1)).save(Mockito.any(Image.class));
    }

    @Test
    public void testDeleteImageById() throws NotFoundException {
        // Arrange
        when(imageRepository.findById(sampleImageDTO.getId())).thenReturn(Optional.of(sampleImage));

        // Act
        assertDoesNotThrow(() -> imageService.deleteImageById(sampleImageDTO.getId()));

        // Assert
        verify(imageRepository, times(1)).deleteById(sampleImageDTO.getId());
    }

}
