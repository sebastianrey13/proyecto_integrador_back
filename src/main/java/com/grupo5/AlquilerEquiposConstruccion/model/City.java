package com.grupo5.AlquilerEquiposConstruccion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "city")
    @JsonIgnore
    private List<Product> product;

    public City() {
    }

    public City(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public City(Integer id, String name, List<Product> product) {
        this.id = id;
        this.name = name;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }
}
