package com.grupo5.AlquilerEquiposConstruccion.dto;

import jakarta.persistence.Column;

public class ProductDTORequest {

    private Integer id;
    private String name;
    @Column(columnDefinition="TEXT")
    private String description;
    @Column(columnDefinition="TEXT")
    private String specifications;
    private boolean active;
    private boolean available;
    private Double average_score;
    private Double costPerDay;

    private Integer category_id;

    private Integer city_id;

    public ProductDTORequest() {
    }

    public ProductDTORequest(Integer id, String name, String description, String specifications, boolean active, boolean available, Double average_score, Double costPerDay, Integer category_id, Integer city_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.specifications = specifications;
        this.active = active;
        this.available = available;
        this.average_score = average_score;
        this.costPerDay = costPerDay;
        this.category_id = category_id;
        this.city_id = city_id;
    }

    public ProductDTORequest(String name, String description, String specifications, boolean active, boolean available, Double average_score, Double costPerDay, Integer category_id, Integer city_id) {
        this.name = name;
        this.description = description;
        this.specifications = specifications;
        this.active = active;
        this.available = available;
        this.average_score = average_score;
        this.costPerDay = costPerDay;
        this.category_id = category_id;
        this.city_id = city_id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Double getAverage_score() {
        return average_score;
    }

    public void setAverage_score(Double average_score) {
        this.average_score = average_score;
    }

    public Double getCostPerDay() {
        return costPerDay;
    }

    public void setCostPerDay(Double costPerDay) {
        this.costPerDay = costPerDay;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", specifications='" + specifications + '\'' +
                ", active=" + active +
                ", available=" + available +
                ", average_score=" + average_score +
                ", costPerDay=" + costPerDay +
                ", category_id=" + category_id +
                ", city_id=" + city_id +
                '}';
    }
}
