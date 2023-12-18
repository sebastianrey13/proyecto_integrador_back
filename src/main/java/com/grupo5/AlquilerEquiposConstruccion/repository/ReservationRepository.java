package com.grupo5.AlquilerEquiposConstruccion.repository;

import com.grupo5.AlquilerEquiposConstruccion.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query("SELECT r FROM Reservation r WHERE r.user.id = :userId")
    List<Reservation> findByUserId(Integer userId);

    @Query("SELECT r FROM Reservation r WHERE r.product.id = :productId")
    List<Reservation> findByProductId(Integer productId);


}
