package com.grupo5.AlquilerEquiposConstruccion.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.ToString;

@Entity
@Table(name="characteristic")

@ToString
public class Characteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String characteristic;
    private String icon;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="product_id", referencedColumnName = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Product product;

    public Characteristic() {
    }

    public Characteristic(Integer id, String characteristic, String icon) {
        this.id = id;
        this.characteristic = characteristic;
        this.icon = icon;
    }

    public Characteristic(Integer id, String characteristic, String icon, Product product) {
        this.id = id;
        this.characteristic = characteristic;
        this.icon = icon;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
