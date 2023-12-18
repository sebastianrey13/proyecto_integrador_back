package com.grupo5.AlquilerEquiposConstruccion.repository;

import com.grupo5.AlquilerEquiposConstruccion.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByProduct_id(Integer id);
    List<Review> findByUser_id(Integer id);
    Optional<Review> findByUser_idAndProduct_id(Integer userId, Integer productId);
}
