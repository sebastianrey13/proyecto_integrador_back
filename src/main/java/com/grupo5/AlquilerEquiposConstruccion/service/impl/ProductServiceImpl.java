package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.CategoryDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.CityDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.ProductDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.ProductDTORequest;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Category;
import com.grupo5.AlquilerEquiposConstruccion.model.City;
import com.grupo5.AlquilerEquiposConstruccion.model.Product;
import com.grupo5.AlquilerEquiposConstruccion.repository.ProductRepository;
import com.grupo5.AlquilerEquiposConstruccion.service.ProductService;
import com.grupo5.AlquilerEquiposConstruccion.utils.ProductMapper;
import jakarta.transaction.Transactional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final Logger logger = Logger.getLogger(ProductServiceImpl.class);

    @Autowired
    @Lazy
    private ProductRepository productRepository;

    @Autowired
    @Lazy
    private CategoryServiceImpl categoryService;

    @Autowired
    @Lazy
    private CityServiceImpl cityService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    ProductMapper mapperProduct;


    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findByActiveTrue();
        List<ProductDTO> productsDTO = new ArrayList<>();

        for (Product product : products) {
            ProductDTO productDTO = mapper.convertValue(product, ProductDTO.class);

            CityDTO cityDTO = mapper.convertValue(product.getCity(), CityDTO.class);
            CategoryDTO categoryDTO = mapper.convertValue(product.getCategory(), CategoryDTO.class);

            productDTO.setCity(cityDTO);
            productDTO.setCategory(categoryDTO);

            productDTO.setPoliciesCancellation("Agregue las fechas de su reservación para obtener los detalles de cancelación de este producto.");

            productsDTO.add(productDTO);
        }

        return productsDTO;
    }

    @Override
    public Optional<ProductDTO> getProductById(Integer id) throws NotFoundException {
        Product productFounded = productRepository.findById(id).orElseThrow(() -> new NotFoundException("The " +
                "product with the id: " + id + " was not found."));
        CityDTO cityDTO = mapper.convertValue(productFounded.getCity(), CityDTO.class);
        CategoryDTO categoryDTO = mapper.convertValue(productFounded.getCategory(), CategoryDTO.class);

        ProductDTO productDTO = mapper.convertValue(productFounded, ProductDTO.class);

        productDTO.setCity(cityDTO);
        productDTO.setCategory(categoryDTO);
        productDTO.setPoliciesCancellation("Agregue las fechas de su reservación para obtener los detalles de cancelación de este producto.");

        return Optional.ofNullable(productDTO);
    }

    @Override
    public ProductDTO createProduct(ProductDTORequest product) throws BadRequestException, NotFoundException {
        if (product.getName() == null || product.getDescription() == null || product.getSpecifications() == null || product.getCostPerDay() == null) {
            throw new BadRequestException("The product has null values.");
        } else {
            Product productCreated = mapper.convertValue(product, Product.class);
            productCreated.setTotalReviews(0);
            productCreated.setTotalScore(0);
            Optional<CategoryDTO> category = categoryService.getCategoryById(product.getCategory_id());
            if (category.isPresent()) {
                Category categoryEntity = mapper.convertValue(category.get(), Category.class);
                productCreated.setCategory(categoryEntity);
            } else {
                throw new NotFoundException("Category not found.");
            }
            Optional<CityDTO> city = cityService.getCityById(product.getCity_id());
            if (city.isPresent()) {
                City cityEntity = mapper.convertValue(city.get(), City.class);
                productCreated.setCity(cityEntity);
            } else {
                throw new NotFoundException("City not found.");
            }
            logger.info("The product was created successfully.");
            Product savedProduct = productRepository.save(productCreated);
            return mapper.convertValue(savedProduct, ProductDTO.class);
        }
    }

    @Override
    public ProductDTO updateProduct(ProductDTO product, Integer id) throws NotFoundException {
        Optional<ProductDTO> existingProduct = getProductById(id);
        if (existingProduct.isPresent()){
            existingProduct.get().setName(product.getName());
            existingProduct.get().setDescription(product.getDescription());
            existingProduct.get().setSpecifications(product.getSpecifications());
            existingProduct.get().setActive(product.isActive());
            existingProduct.get().setAvailable(product.isAvailable());
            existingProduct.get().setAverage_score(product.getAverage_score());
            existingProduct.get().setCostPerDay(product.getCostPerDay());
            existingProduct.get().setTotalReviews(product.getTotalReviews());
            existingProduct.get().setTotalScore(product.getTotalScore());
            if (existingProduct.get().getTotalReviews()<=0){
                existingProduct.get().setTotalScore(0);
                existingProduct.get().setAverage_score(0.0);
            } else if (existingProduct.get().getTotalReviews()>0){
                existingProduct.get().setAverage_score((double) ((product.getTotalScore()) / (product.getTotalReviews())));
            }
            Product productUpdated = mapper.convertValue(existingProduct, Product.class);
            Optional<CategoryDTO> category = categoryService.getCategoryById(product.getCategory().getId());
            if (category.isPresent()) {
                Category categoryEntity = mapper.convertValue(category.get(), Category.class);
                productUpdated.setCategory(categoryEntity);
            } else {
                throw new NotFoundException("Category not found.");
            }
            Optional<CityDTO> city = cityService.getCityById(product.getCity().getId());
            if (city.isPresent()) {
                City cityEntity = mapper.convertValue(city.get(), City.class);
                productUpdated.setCity(cityEntity);
            } else {
                throw new NotFoundException("City not found.");
            }
            Product updatedProduct = productRepository.save(productUpdated);
            logger.info("The product was updated successfully.");

            return mapper.convertValue(updatedProduct, ProductDTO.class);
        } else {
            throw new NotFoundException("Product not found.");
        }
    }

    @Override
    public void deleteProductById(Integer id) throws NotFoundException {
        Optional<Product> productFounded = productRepository.findById(id);
        if (productFounded.isPresent()) {
            Product product = productFounded.get();
            product.setActive(false);
            product.setAvailable(false);
            productRepository.save(product);
        } else {
            throw new NotFoundException("The product with ID " + id + " was not found.");
        }
    }

    @Override
    public List<ProductDTO> getProductsByCategory(String name) {

        List<Product> productsByCategory = productRepository.findByCategoryNameAndActiveTrue(name);

        List<ProductDTO> productDTOList = productsByCategory.stream()
                .map(product -> mapper.convertValue(product, ProductDTO.class))
                .collect(Collectors.toList());

        return productDTOList;
    }
    @Override
    public List<ProductDTO> getProductsByCity(String name) {
        List<Product> productsByCity = productRepository.findByCityNameAndActiveTrue(name);

        List<ProductDTO> productDTOList = productsByCity.stream()
                .map(product -> mapper.convertValue(product, ProductDTO.class))
                .collect(Collectors.toList());

        return productDTOList;
    }
    @Override
    public List<ProductDTO> getRandomProduct() {
        long totalProducts = productRepository.countByActiveTrue();

        Set<Integer> randomIndexes = new HashSet<>();
        Random random = new Random();
        while (randomIndexes.size() < 10) {
            int randomIndex = random.nextInt((int) totalProducts);
            randomIndexes.add(randomIndex);
        }

        List<Product> randomProducts = productRepository.findByIdAndActiveTrue(randomIndexes);

        return randomProducts.stream()
                .map(product -> mapper.convertValue(product, ProductDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public List<ProductDTO> getAllSuggestionsProducts(String input) {
        List<Product> productNameContaining = productRepository.findByProductNameContainingAndActiveTrue(input);

        List<ProductDTO> productDTOList = productNameContaining.stream()
                .map(product -> mapper.convertValue(product, ProductDTO.class))
                .collect(Collectors.toList());

        return productDTOList;
    }

}
