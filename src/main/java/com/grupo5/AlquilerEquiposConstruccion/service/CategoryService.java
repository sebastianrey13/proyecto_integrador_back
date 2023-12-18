package com.grupo5.AlquilerEquiposConstruccion.service;

import com.grupo5.AlquilerEquiposConstruccion.dto.CategoryDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    Optional<CategoryDTO> getCategoryById(Integer id) throws NotFoundException;
    CategoryDTO createCategory(CategoryDTO category) throws BadRequestException;
    CategoryDTO updateCategory(CategoryDTO category, Integer id) throws NotFoundException;
    void deleteCategory(Integer id) throws NotFoundException;
}
