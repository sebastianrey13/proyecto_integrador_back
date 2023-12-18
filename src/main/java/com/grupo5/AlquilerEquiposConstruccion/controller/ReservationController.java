package com.grupo5.AlquilerEquiposConstruccion.controller;

import com.grupo5.AlquilerEquiposConstruccion.dto.ReservationDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByUserId(@PathVariable Integer userId) {
        List<ReservationDTO> reservations = reservationService.findByUserId(userId);
        if (!reservations.isEmpty()) {
            return ResponseEntity.ok(reservations);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/by-product/{productId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByProductId(@PathVariable Integer productId) {
        List<ReservationDTO> reservations = reservationService.findByProductId(productId);
        if (!reservations.isEmpty()) {
            return ResponseEntity.ok(reservations);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        List<ReservationDTO> reservations = reservationService.getAllReservation();
        if (!reservations.isEmpty()) {
            return ResponseEntity.ok(reservations);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Integer id) {
        try {
            Optional<ReservationDTO> reservation = reservationService.getReservationById(id);
            return ResponseEntity.of(reservation);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) throws NotFoundException {
        ReservationDTO savedReservation = reservationService.saveReservation(reservationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReservation);
    }

    @PutMapping("/update")
    public ResponseEntity<ReservationDTO> updateReservation(@RequestBody ReservationDTO reservationDTO) {
        try {
            ReservationDTO updatedReservation = reservationService.updateReservation(reservationDTO);
            return ResponseEntity.ok(updatedReservation);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Integer id) {
        reservationService.deleteReservationById(id);
        return ResponseEntity.noContent().build();
    }
}


