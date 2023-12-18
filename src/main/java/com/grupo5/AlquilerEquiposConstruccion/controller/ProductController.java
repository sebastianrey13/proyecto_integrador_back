package com.grupo5.AlquilerEquiposConstruccion.controller;

import com.grupo5.AlquilerEquiposConstruccion.dto.ProductDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.ProductDTORequest;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id) throws NotFoundException {
        Optional<ProductDTO> productSearch = productService.getProductById(id);
        if(productSearch.isPresent()) {
            return ResponseEntity.ok(productSearch.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTORequest product) throws BadRequestException, NotFoundException {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO product) throws Exception{
        Optional<ProductDTO> productSearch = productService.getProductById(product.getId());
        if(productSearch.isPresent()){
            return ResponseEntity.ok(productService.updateProduct(product, product.getId()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id: " + product.getId() + " was not found.");
        }

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) throws NotFoundException {
        if (productService.getProductById(id).isPresent()) {
            productService.deleteProductById(id);
            return ResponseEntity.ok("The product with id: " + id + " was deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id: " + id + " was not found.");
    }

    @GetMapping("/by-category/{categoryName}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable String categoryName) throws NotFoundException {
        List<ProductDTO> productsByCategory = productService.getProductsByCategory(categoryName);
        if (!productsByCategory.isEmpty()) {
            return ResponseEntity.ok(productsByCategory);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/by-city/{cityName}")
    public ResponseEntity<List<ProductDTO>> getProductsByCity(@PathVariable String cityName) throws NotFoundException{
        List<ProductDTO> productsByCity = productService.getProductsByCity(cityName);
        if (!productsByCity.isEmpty()) {
            return ResponseEntity.ok(productsByCity);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/random")
    public ResponseEntity<List<ProductDTO>> getRandomProducts() {
        List<ProductDTO> randomProducts = productService.getRandomProduct();
        if (!randomProducts.isEmpty()) {
            return ResponseEntity.ok(randomProducts);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/by-input/{input}")
    public ResponseEntity<List<ProductDTO>> getSuggestionsProducts(@PathVariable String input){
        return ResponseEntity.ok(productService.getAllSuggestionsProducts(input));
    }


}
