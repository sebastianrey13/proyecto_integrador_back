package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.CategoryDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.ProductDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Category;
import com.grupo5.AlquilerEquiposConstruccion.repository.CategoryRepository;
import com.grupo5.AlquilerEquiposConstruccion.service.CategoryService;
import org.apache.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final Logger logger = Logger.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoriesDTO = new ArrayList<>();
        for (Category category : categories){
            categoriesDTO.add(mapper.convertValue(category,CategoryDTO.class));
        }
        return categoriesDTO;
    }

    @Override
    public Optional<CategoryDTO> getCategoryById(Integer id) throws NotFoundException {
        Category categoryFounded = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("The " +
                "category with the id: " + id + " was not found."));
        return Optional.ofNullable(mapper.convertValue(categoryFounded, CategoryDTO.class));
    }

//    @Override
//    public CategoryDTO createCategory(CategoryDTO category) throws BadRequestException {
//        if (category.getName()==null || category.getDescription()==null || category.getUrlImage()==null){
//            throw new BadRequestException("The category has null values.");
//        } else{
//            Category categoryCreated = mapper.convertValue(category, Category.class);
//            logger.info("The category was created successfully.");
//            return mapper.convertValue(categoryRepository.save(categoryCreated), CategoryDTO.class);
//        }
//    }

    @Override
    public CategoryDTO createCategory(CategoryDTO category) throws BadRequestException {
        if (category.getName()==null || category.getDescription()==null){
            throw new BadRequestException("The category has null values.");
        } else{
            Category categoryCreated = mapper.convertValue(category, Category.class);
            logger.info("The category was created successfully.");
            return mapper.convertValue(categoryRepository.save(categoryCreated), CategoryDTO.class);
        }
    }

//    @Override
//    public CategoryDTO updateCategory(CategoryDTO category, Integer id) throws NotFoundException {
//        Optional<CategoryDTO> existingCategory = getCategoryById(id);
//        if (existingCategory.isPresent()){
//            existingCategory.get().setName(category.getName());
//            existingCategory.get().setDescription(category.getDescription());
//            existingCategory.get().setUrlImage(category.getUrlImage());
//            Category categoryUpdated = mapper.convertValue(existingCategory, Category.class);
//            categoryRepository.save(categoryUpdated);
//            logger.info("The category was updated successfully.");
//        }
//        return mapper.convertValue(existingCategory, CategoryDTO.class);
//    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO category, Integer id) throws NotFoundException {
        Optional<CategoryDTO> existingCategory = getCategoryById(id);
        if (existingCategory.isPresent()) {
            CategoryDTO categoryToUpdate = existingCategory.get();
            categoryToUpdate.setName(category.getName());
            categoryToUpdate.setDescription(category.getDescription());
            categoryToUpdate.setUrlImage(category.getUrlImage());

            Category categoryUpdated = mapper.convertValue(categoryToUpdate, Category.class);
            categoryRepository.save(categoryUpdated);

            logger.info("The category was updated successfully.");
        }
        return existingCategory.orElse(null);
    }


    @Override
    public void deleteCategory(Integer id) throws NotFoundException {
        Optional<CategoryDTO> categoryFounded = getCategoryById(id);
        List<ProductDTO> productsFounded = productService.getProductsByCategory(categoryFounded.get().getName());
        if(productsFounded.size() > 1) {
            throw new ServiceException("This category can't be deleted because there are products associated with it.");
        } else {
            categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("The " +
                    "category with the id: " + id + " was not found."));
            categoryRepository.deleteById(id);
        }
    }
}
