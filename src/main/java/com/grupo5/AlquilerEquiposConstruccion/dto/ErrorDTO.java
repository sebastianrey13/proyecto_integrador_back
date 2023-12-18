package com.grupo5.AlquilerEquiposConstruccion.dto;

public class ErrorDTO {
    private String message;
    private int status;

    public ErrorDTO() {
    }

    public ErrorDTO(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
