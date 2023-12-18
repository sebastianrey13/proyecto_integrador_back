package com.grupo5.AlquilerEquiposConstruccion.controller;

import com.grupo5.AlquilerEquiposConstruccion.dto.CityDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<List<CityDTO>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDTO> getCityById(@PathVariable Integer id) throws NotFoundException {
        Optional<CityDTO> citySearch = cityService.getCityById(id);
        if (citySearch.isPresent()) {
            return ResponseEntity.ok(citySearch.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<CityDTO> createCity(@RequestBody CityDTO city) throws BadRequestException {
        return ResponseEntity.ok(cityService.saveCity(city));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCity(@RequestBody CityDTO city) throws Exception {
        Optional<CityDTO> citySearch = cityService.getCityById(city.getId());
        if (citySearch.isPresent()) {
            return ResponseEntity.ok(cityService.updateCity(city, citySearch.get().getId()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("City with ID: " + city.getId() + " was not found.");
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> eliminarCiudad(@PathVariable Integer id) throws NotFoundException {
        if (cityService.getCityById(id).isPresent()) {
            cityService.deleteCityById(id);
            return ResponseEntity.ok("The city with id: " + id + " was deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("City with id: " + id + " was not found.");
    }
}