package com.grupo5.AlquilerEquiposConstruccion.dto;

import com.grupo5.AlquilerEquiposConstruccion.model.Product;
import lombok.ToString;

@ToString
public class ImageDTO {
    private Integer id;
    private String title;
    private String url;
    private Product product;

    public ImageDTO() {
    }

    public ImageDTO(Integer id, String title, String url, Product product) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.product = product;
    }

    public ImageDTO(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public ImageDTO(String title, String url, Product product) {
        this.title = title;
        this.url = url;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
