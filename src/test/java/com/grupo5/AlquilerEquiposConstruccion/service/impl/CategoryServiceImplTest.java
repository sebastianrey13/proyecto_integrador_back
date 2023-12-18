package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.CategoryDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Category;
import com.grupo5.AlquilerEquiposConstruccion.repository.CategoryRepository;
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
class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductServiceImpl productService;

    @Mock
    private ObjectMapper objectMapper;

    private CategoryDTO sampleCategoryDTO;
    private Category sampleCategory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleCategoryDTO = new CategoryDTO(1, "Sample Category", "Sample Description", "Sample Image URL");
        sampleCategory = new Category(1, "Sample Category", "Sample Description", "Sample Image URL");

        when(objectMapper.convertValue(eq(sampleCategoryDTO), eq(Category.class)))
                .thenReturn(sampleCategory);
        when(objectMapper.convertValue(eq(sampleCategory), eq(CategoryDTO.class)))
                .thenReturn(sampleCategoryDTO);
    }

    @Test
    public void testGetAllCategories() {
        // Arrange
        when(categoryRepository.findAll()).thenReturn(List.of(sampleCategory));

        // Act
        List<CategoryDTO> result = categoryService.getAllCategories();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(sampleCategoryDTO.getId(), result.get(0).getId());
    }

    @Test
    public void testGetCategoryById() throws NotFoundException {
        // Arrange
        when(categoryRepository.findById(sampleCategoryDTO.getId())).thenReturn(Optional.of(sampleCategory));

        // Act
        Optional<CategoryDTO> result = categoryService.getCategoryById(sampleCategoryDTO.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(sampleCategoryDTO.getId(), result.get().getId());
    }

    @Test
    public void testCreateCategory() throws BadRequestException {
        // Arrange
        when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(sampleCategory);

        // Act
        CategoryDTO result = categoryService.createCategory(sampleCategoryDTO);

        // Assert
        assertNotNull(result);
        assertEquals(sampleCategoryDTO.getId(), result.getId());
        assertEquals(sampleCategoryDTO.getName(), result.getName());
        assertEquals(sampleCategoryDTO.getDescription(), result.getDescription());
        assertEquals(sampleCategoryDTO.getUrlImage(), result.getUrlImage());
    }

    @Test
    public void testUpdateCategory() throws NotFoundException {
        // Arrange
        when(categoryRepository.findById(sampleCategoryDTO.getId())).thenReturn(Optional.of(sampleCategory));
        when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(sampleCategory);

        // Act
        CategoryDTO result = categoryService.updateCategory(sampleCategoryDTO, sampleCategoryDTO.getId());

        // Assert
        assertNotNull(result);
        assertEquals(sampleCategoryDTO.getId(), result.getId());
        assertEquals(sampleCategoryDTO.getName(), result.getName());
        assertEquals(sampleCategoryDTO.getDescription(), result.getDescription());
        assertEquals(sampleCategoryDTO.getUrlImage(), result.getUrlImage());

        verify(categoryRepository, times(1)).findById(sampleCategoryDTO.getId());
        verify(categoryRepository, times(1)).save(Mockito.any(Category.class));
    }


    @Test
    public void testDeleteCategory() throws NotFoundException {
        // Arrange
        when(categoryRepository.findById(sampleCategoryDTO.getId())).thenReturn(Optional.of(sampleCategory));
        when(productService.getProductsByCategory(sampleCategoryDTO.getName())).thenReturn(List.of());

        // Act
        assertDoesNotThrow(() -> categoryService.deleteCategory(sampleCategoryDTO.getId()));

        // Assert
        verify(categoryRepository, times(1)).deleteById(sampleCategoryDTO.getId());
    }
}
