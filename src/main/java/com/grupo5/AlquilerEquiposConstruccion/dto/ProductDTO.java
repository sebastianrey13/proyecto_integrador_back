package com.grupo5.AlquilerEquiposConstruccion.dto;

import jakarta.persistence.Column;

public class ProductDTO {

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

    private CityDTO city;

    private CategoryDTO category;

    private String policiesCancellation;

    private Integer total_reviews;

    private Integer total_score;


    public ProductDTO() {
    }

    public ProductDTO(Integer id, String name, String description, String specifications, boolean active, boolean available, Double average_score, Double costPerDay, CityDTO city, CategoryDTO category, String policiesCancellation, Integer total_reviews, Integer total_score) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.specifications = specifications;
        this.active = active;
        this.available = available;
        this.average_score = average_score;
        this.costPerDay = costPerDay;
        this.city = city;
        this.category = category;
        this.policiesCancellation = policiesCancellation;
        this.total_reviews = total_reviews;
        this.total_score = total_score;
    }

    public ProductDTO(String name, String description, String specifications, boolean active, boolean available, Double average_score, Double costPerDay, CityDTO city, CategoryDTO category, String policiesCancellation, Integer total_reviews, Integer total_score) {
        this.name = name;
        this.description = description;
        this.specifications = specifications;
        this.active = active;
        this.available = available;
        this.average_score = average_score;
        this.costPerDay = costPerDay;
        this.city = city;
        this.category = category;
        this.policiesCancellation = policiesCancellation;
        this.total_reviews = total_reviews;
        this.total_score = total_score;
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

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public String getPoliciesCancellation() {
        return policiesCancellation;
    }

    public void setPoliciesCancellation(String policiesCancellation) {
        this.policiesCancellation = policiesCancellation;
    }

    public Integer getTotalReviews() {
        return total_reviews;
    }

    public void setTotalReviews(Integer total_reviews) {
        this.total_reviews = total_reviews;
    }

    public Integer getTotalScore() {
        return total_score;
    }

    public void setTotalScore(Integer total_score) {
        this.total_score = total_score;
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
                ", city=" + city +
                ", category=" + category +
                ", policiesCancellation='" + policiesCancellation + '\'' +
                ", totalReviews=" + total_reviews +
                ", totalScore=" + total_score +
                '}';
    }
}
