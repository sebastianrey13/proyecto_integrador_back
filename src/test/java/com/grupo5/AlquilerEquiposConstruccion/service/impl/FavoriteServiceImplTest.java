package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.FavoriteDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Favorite;
import com.grupo5.AlquilerEquiposConstruccion.model.Product;
import com.grupo5.AlquilerEquiposConstruccion.model.User;
import com.grupo5.AlquilerEquiposConstruccion.repository.FavoriteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class FavoriteServiceImplTest {

    @InjectMocks
    private FavoriteServiceImpl favoriteService;

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private ObjectMapper objectMapper;

    private FavoriteDTO sampleFavoriteDTO;
    private Favorite sampleFavorite;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleFavoriteDTO = new FavoriteDTO(1, new Product(), new User());
        sampleFavorite = new Favorite(1, new Product(), new User());

        when(objectMapper.convertValue(eq(sampleFavoriteDTO), eq(Favorite.class)))
                .thenReturn(sampleFavorite);
        when(objectMapper.convertValue(eq(sampleFavorite), eq(FavoriteDTO.class)))
                .thenReturn(sampleFavoriteDTO);
    }

    @Test
    public void testGetAllFavorites() {
        // Arrange
        when(favoriteRepository.findAll()).thenReturn(List.of(sampleFavorite));

        // Act
        List<FavoriteDTO> result = favoriteService.getAllFavorites();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(sampleFavoriteDTO.getId(), result.get(0).getId());
    }

    @Test
    public void testGetFavoriteById() throws NotFoundException {
        // Arrange
        when(favoriteRepository.findById(sampleFavoriteDTO.getId())).thenReturn(Optional.of(sampleFavorite));

        // Act
        Optional<FavoriteDTO> result = favoriteService.getFavoriteById(sampleFavoriteDTO.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(sampleFavoriteDTO.getId(), result.get().getId());
    }

    @Test
    public void testCreateFavorite() throws BadRequestException {
        // Arrange
        when(favoriteRepository.save(Mockito.any(Favorite.class))).thenReturn(sampleFavorite);

        // Act
        FavoriteDTO result = favoriteService.createFavorite(sampleFavoriteDTO);

        // Assert
        assertNotNull(result);
        assertEquals(sampleFavoriteDTO.getId(), result.getId());
    }

    @Test
    public void testUpdateFavorite() throws NotFoundException {
        // Arrange
        when(favoriteRepository.findById(sampleFavoriteDTO.getId())).thenReturn(Optional.of(sampleFavorite));
        when(favoriteRepository.save(Mockito.any(Favorite.class))).thenReturn(sampleFavorite);

        // Act
        FavoriteDTO result = favoriteService.updateFavorite(sampleFavoriteDTO);

        // Assert
        assertNotNull(result);
        assertEquals(sampleFavoriteDTO.getId(), result.getId());

        verify(favoriteRepository, times(1)).save(Mockito.any(Favorite.class));
    }

    @Test
    public void testDeleteFavoriteById() throws NotFoundException {
        // Arrange
        when(favoriteRepository.findById(sampleFavoriteDTO.getId())).thenReturn(Optional.of(sampleFavorite));

        // Act
        assertDoesNotThrow(() -> favoriteService.deleteFavoriteById(sampleFavoriteDTO.getId()));

        // Assert
        verify(favoriteRepository, times(1)).deleteById(sampleFavoriteDTO.getId());
    }

}
