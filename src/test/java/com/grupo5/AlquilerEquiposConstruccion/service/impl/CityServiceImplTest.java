package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.CityDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.City;
import com.grupo5.AlquilerEquiposConstruccion.repository.CityRepository;
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
class CityServiceImplTest {

    @InjectMocks
    private CityServiceImpl cityService;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private ObjectMapper objectMapper;

    private CityDTO sampleCityDTO;
    private City sampleCity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleCityDTO = new CityDTO(1, "Sample City");
        sampleCity = new City(1, "Sample City");

        when(objectMapper.convertValue(eq(sampleCityDTO), eq(City.class)))
                .thenReturn(sampleCity);
        when(objectMapper.convertValue(eq(sampleCity), eq(CityDTO.class)))
                .thenReturn(sampleCityDTO);
    }

    @Test
    public void testGetAllCities() {
        // Arrange
        when(cityRepository.findAll()).thenReturn(List.of(sampleCity));

        // Act
        List<CityDTO> result = cityService.getAllCities();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(sampleCityDTO.getId(), result.get(0).getId());
    }

    @Test
    public void testGetCityById() throws NotFoundException {
        // Arrange
        when(cityRepository.findById(sampleCityDTO.getId())).thenReturn(Optional.of(sampleCity));

        // Act
        Optional<CityDTO> result = cityService.getCityById(sampleCityDTO.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(sampleCityDTO.getId(), result.get().getId());
    }

    @Test
    public void testSaveCity() throws BadRequestException {
        // Arrange
        when(cityRepository.save(Mockito.any(City.class))).thenReturn(sampleCity);

        // Act
        CityDTO result = cityService.saveCity(sampleCityDTO);

        // Assert
        assertNotNull(result);
        assertEquals(sampleCityDTO.getId(), result.getId());
        assertEquals(sampleCityDTO.getName(), result.getName());
    }

    @Test
    public void testUpdateCity() throws NotFoundException {
        // Arrange
        when(cityRepository.findById(sampleCityDTO.getId())).thenReturn(Optional.of(sampleCity));
        when(cityRepository.save(Mockito.any(City.class))).thenReturn(sampleCity);

        // Act
        CityDTO result = cityService.updateCity(sampleCityDTO, sampleCityDTO.getId());

        // Assert
        assertNotNull(result);
        assertEquals(sampleCityDTO.getId(), result.getId());
        assertEquals(sampleCityDTO.getName(), result.getName());

        verify(cityRepository, times(1)).findById(sampleCityDTO.getId());
        verify(cityRepository, times(1)).save(Mockito.any(City.class));
    }

    @Test
    public void testDeleteCityById() throws NotFoundException {
        // Arrange
        when(cityRepository.findById(sampleCityDTO.getId())).thenReturn(Optional.of(sampleCity));

        // Act
        assertDoesNotThrow(() -> cityService.deleteCityById(sampleCityDTO.getId()));

        // Assert
        verify(cityRepository, times(1)).deleteById(sampleCityDTO.getId());
    }
}
