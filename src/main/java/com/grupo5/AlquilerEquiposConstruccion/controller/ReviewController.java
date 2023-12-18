package com.grupo5.AlquilerEquiposConstruccion.controller;

import com.grupo5.AlquilerEquiposConstruccion.dto.FavoriteDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.ReviewDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Integer id) throws NotFoundException {
        Optional<ReviewDTO> reviewSearch = reviewService.getReviewById(id);
        if (reviewSearch.isPresent()) {
            return ResponseEntity.ok(reviewSearch.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO review) throws BadRequestException, NotFoundException {
        return ResponseEntity.ok(reviewService.createReview(review));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateReview(@RequestBody ReviewDTO review) throws Exception {
        Optional<ReviewDTO> reviewSearch = reviewService.getReviewById(review.getId());
        if (reviewSearch.isPresent()) {
            return ResponseEntity.ok(reviewService.updateReview(review));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review with ID: " + review.getId() + " was not found.");
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReviewById(@PathVariable Integer id) throws NotFoundException {
        if (reviewService.getReviewById(id).isPresent()) {
            reviewService.deleteReviewById(id);
            return ResponseEntity.ok("The review with id: " + id + " was deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review with id: " + id + " was not found.");
    }

    @GetMapping("/by-product/{id}")
    public ResponseEntity<List<ReviewDTO>> findAllByProduct_id(@PathVariable Integer id) throws NotFoundException {
        List<ReviewDTO> reviewSearch = reviewService.findByProduct_id(id);
        if (!reviewSearch.isEmpty()) {
            return ResponseEntity.ok(reviewSearch);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<List<ReviewDTO>> findAllByUser_id(@PathVariable Integer id) throws NotFoundException {
        List<ReviewDTO> reviewSearch = reviewService.findByUser_id(id);
        if (!reviewSearch.isEmpty()) {
            return ResponseEntity.ok(reviewSearch);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/by-user-and-product/{userId}/{productId}")
    public ResponseEntity<ReviewDTO> findByUser_idAndProduct_id(@PathVariable Integer userId, @PathVariable Integer productId) throws NotFoundException {
        Optional<ReviewDTO> reviewSearch = reviewService.findByUser_idAndProduct_id(userId, productId);
        if (reviewSearch.isPresent()) {
            return ResponseEntity.ok(reviewSearch.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
