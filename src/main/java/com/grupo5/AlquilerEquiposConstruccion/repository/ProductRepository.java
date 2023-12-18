package com.grupo5.AlquilerEquiposConstruccion.repository;

import com.grupo5.AlquilerEquiposConstruccion.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName AND p.active = true")
    List<Product> findByCategoryNameAndActiveTrue(@Param("categoryName") String categoryName);

    @Query("SELECT p FROM Product p WHERE p.city.name = :cityName AND p.active = true")
    List<Product> findByCityNameAndActiveTrue(@Param("cityName") String cityName);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name% AND p.active = true")
    List<Product> findByProductNameContainingAndActiveTrue(@Param("name") String name);

    List<Product> findByActiveTrue();

    @Query("SELECT p FROM Product p WHERE p.id IN :ids AND p.active = true")
    List<Product> findByIdAndActiveTrue(@Param("ids") Iterable<Integer> ids);

    long countByActiveTrue();
}

