package com.grupo5.AlquilerEquiposConstruccion.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.grupo5.AlquilerEquiposConstruccion.model.Product;
import com.grupo5.AlquilerEquiposConstruccion.model.User;

import java.time.LocalDate;

public class ReservationDTO {
    private Integer id;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate check_in_date;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate checkout_date;
    private String comments;

    private Product product;

    private User user;

    private boolean active;

    public ReservationDTO() {
    }

    public ReservationDTO(Integer id, LocalDate check_in_date, LocalDate checkout_date, String comments, Product product, User user) {
        this.id = id;
        this.check_in_date = check_in_date;
        this.checkout_date = checkout_date;
        this.comments = comments;
        this.product = product;
        this.user = user;
    }

    public ReservationDTO(LocalDate check_in_date, LocalDate checkout_date, String comments, Product product, User user, boolean active) {
        this.check_in_date = check_in_date;
        this.checkout_date = checkout_date;
        this.comments = comments;
        this.product = product;
        this.user = user;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getCheck_in_date() {
        return check_in_date;
    }

    public void setCheck_in_date(LocalDate check_in_date) {
        this.check_in_date = check_in_date;
    }

    public LocalDate getCheckout_date() {
        return checkout_date;
    }

    public void setCheckout_date(LocalDate checkout_date) {
        this.checkout_date = checkout_date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "id=" + id +
                ", check_in_date=" + check_in_date +
                ", checkout_date=" + checkout_date +
                ", comments='" + comments + '\'' +
                ", product=" + product +
                ", user=" + user +
                ", active=" + active +
                '}';
    }
}
