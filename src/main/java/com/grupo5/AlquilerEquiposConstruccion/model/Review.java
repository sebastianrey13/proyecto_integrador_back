package com.grupo5.AlquilerEquiposConstruccion.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate publication_date;

    private String review;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private Integer score;

    public Review() {
    }

    public Review(Integer id, LocalDate publication_date, String review, Product product, User user, Integer score) {
        this.id = id;
        this.publication_date = publication_date;
        this.review = review;
        this.product = product;
        this.user = user;
        this.score = score;
    }

    public Review(LocalDate publication_date, String review, Product product, User user, Integer score) {
        this.publication_date = publication_date;
        this.review = review;
        this.product = product;
        this.user = user;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getPublication_date() {
        return publication_date;
    }

    public void setPublication_date(LocalDate publication_date) {
        this.publication_date = publication_date;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", publication_date=" + publication_date +
                ", review='" + review + '\'' +
                ", product=" + product +
                ", user=" + user +
                ", score=" + score +
                '}';
    }
}
