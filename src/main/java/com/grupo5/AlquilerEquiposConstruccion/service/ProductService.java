package com.grupo5.AlquilerEquiposConstruccion.service;

import com.grupo5.AlquilerEquiposConstruccion.dto.ProductDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.ProductDTORequest;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDTO> getAllProducts() ;
    Optional<ProductDTO> getProductById(Integer id) throws NotFoundException;
    ProductDTO createProduct(ProductDTORequest product) throws BadRequestException, NotFoundException;
    ProductDTO updateProduct(ProductDTO product,Integer id)throws NotFoundException;
    void deleteProductById(Integer id) throws NotFoundException;
    List<ProductDTO>getProductsByCategory(String name)throws NotFoundException;
    List<ProductDTO>getProductsByCity(String name) throws NotFoundException;
    List<ProductDTO> getRandomProduct();
    List<ProductDTO> getAllSuggestionsProducts(String input);
}
