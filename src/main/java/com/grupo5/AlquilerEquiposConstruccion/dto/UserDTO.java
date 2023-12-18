package com.grupo5.AlquilerEquiposConstruccion.dto;

public class UserDTO {

    private int id;
    private String name;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private boolean enabled;

    private CityDTO city;

    private RoleDTO role;

    public UserDTO() {
    }

    public UserDTO(int id, String name, String lastName, String email, String phoneNumber, String password, boolean enabled, CityDTO city, RoleDTO role) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.enabled = enabled;
        this.city = city;
        this.role = role;
    }

    public UserDTO(String name, String lastName, String email, String phoneNumber, String password, boolean enabled, CityDTO city, RoleDTO role) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.enabled = enabled;
        this.city = city;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", city=" + city +
                ", role=" + role +
                '}';
    }
}
