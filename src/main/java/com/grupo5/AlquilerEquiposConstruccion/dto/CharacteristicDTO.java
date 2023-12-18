package com.grupo5.AlquilerEquiposConstruccion.dto;

import com.grupo5.AlquilerEquiposConstruccion.model.Product;
import lombok.ToString;

@ToString
public class CharacteristicDTO {

    private Integer id;
    private String characteristic;
    private String icon;
    private Product product;

    public CharacteristicDTO() {
    }

    public CharacteristicDTO(Integer id, String characteristic, String icon, Product product) {
        this.id = id;
        this.characteristic = characteristic;
        this.icon = icon;
        this.product = product;
    }

    public CharacteristicDTO(String characteristic, String icon) {
        this.characteristic = characteristic;
        this.icon = icon;
    }

    public CharacteristicDTO(String characteristic, String icon, Product product) {
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
