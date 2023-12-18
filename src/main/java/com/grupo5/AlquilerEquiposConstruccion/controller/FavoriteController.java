package com.grupo5.AlquilerEquiposConstruccion.controller;

import com.grupo5.AlquilerEquiposConstruccion.dto.FavoriteDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<List<FavoriteDTO>> getAllFavorites() {
        return ResponseEntity.ok(favoriteService.getAllFavorites());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoriteDTO> getFavoriteById(@PathVariable Integer id) throws NotFoundException {
        Optional<FavoriteDTO> favoriteSearch = favoriteService.getFavoriteById(id);
        if (favoriteSearch.isPresent()) {
            return ResponseEntity.ok(favoriteSearch.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<FavoriteDTO> createFavorite(@RequestBody FavoriteDTO favorite) throws BadRequestException {
        return ResponseEntity.ok(favoriteService.createFavorite(favorite));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateFavorite(@RequestBody FavoriteDTO favorite) throws Exception {
        Optional<FavoriteDTO> favoriteSearch = favoriteService.getFavoriteById(favorite.getId());
        if (favoriteSearch.isPresent()) {
            return ResponseEntity.ok(favoriteService.updateFavorite(favorite));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Favorite with ID: " + favorite.getId() + " was not found.");
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFavoriteById(@PathVariable Integer id) throws NotFoundException {
        if (favoriteService.getFavoriteById(id).isPresent()) {
            favoriteService.deleteFavoriteById(id);
            return ResponseEntity.ok("The favorite with id: " + id + " was deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Favorite with id: " + id + " was not found.");
    }

    @GetMapping("/by-product/{id}")
    public ResponseEntity<List<FavoriteDTO>> findAllByProduct_id(@PathVariable Integer id) throws NotFoundException {
        List<FavoriteDTO> favoriteSearch = favoriteService.findByProduct_id(id);
        if (!favoriteSearch.isEmpty()) {
            return ResponseEntity.ok(favoriteSearch);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<List<FavoriteDTO>> findAllByUser_id(@PathVariable Integer id) throws NotFoundException {
        List<FavoriteDTO> favoriteSearch = favoriteService.findByUser_id(id);
        if (!favoriteSearch.isEmpty()) {
            return ResponseEntity.ok(favoriteSearch);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/by-user-and-product/{userId}/{productId}")
    public ResponseEntity<Map<String, Object>> findByUser_idAndProduct_id(@PathVariable Integer userId, @PathVariable Integer productId) throws NotFoundException {
        Optional<FavoriteDTO> favoriteSearch = favoriteService.findByUser_idAndProduct_id(userId, productId);
        if (favoriteSearch.isPresent()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "true");
            responseBody.put("status", HttpStatus.OK.value());
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/by-user-and-product/{userId}/{productId}")
    public ResponseEntity<String> deleteByUser_idAndProduct_id(@PathVariable Integer userId, @PathVariable Integer productId) throws NotFoundException {
        Integer favoriteId = favoriteService.findByUser_idAndProduct_id(userId, productId).get().getId();
        if (favoriteService.findByUser_idAndProduct_id(userId, productId).isPresent()) {
            favoriteService.deleteByUser_idAndProduct_id(userId, productId);
            return ResponseEntity.ok("The favorite with id: " + favoriteId + " was deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Favorite with id: " + favoriteId + " was not found.");
    }
}
