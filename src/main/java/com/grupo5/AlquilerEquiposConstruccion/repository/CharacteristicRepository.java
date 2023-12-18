package com.grupo5.AlquilerEquiposConstruccion.repository;

import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Characteristic;
import com.grupo5.AlquilerEquiposConstruccion.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacteristicRepository extends JpaRepository<Characteristic, Integer> {
    List<Characteristic> findByproduct_id(Integer id);
    void deleteByProduct_id(Integer id) throws NotFoundException;
}
