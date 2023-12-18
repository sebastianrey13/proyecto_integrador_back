package com.grupo5.AlquilerEquiposConstruccion.exceptions;

import com.grupo5.AlquilerEquiposConstruccion.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalHandlerException {


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDTO> ResourceNotFoundException(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(exception.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDTO> UserBadRequestException(BadRequestException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(exception.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }
}
