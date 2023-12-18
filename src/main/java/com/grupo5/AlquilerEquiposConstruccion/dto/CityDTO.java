package com.grupo5.AlquilerEquiposConstruccion.dto;

public class CityDTO {

    private Integer id;
    private String name;

    public CityDTO() {
    }

    public CityDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public CityDTO(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "CityDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
