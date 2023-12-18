package com.grupo5.AlquilerEquiposConstruccion.controller;

import com.grupo5.AlquilerEquiposConstruccion.dto.LoginDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.LoginDTOResponse;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.security.config.JwtUtil;
import com.grupo5.AlquilerEquiposConstruccion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTOResponse> login(@RequestBody LoginDTO loginDto) throws NotFoundException {
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(login);

        System.out.println(authentication.isAuthenticated());
        System.out.println(authentication.getPrincipal());

        String jwt = this.jwtUtil.create(loginDto.getUsername());

        String username = loginDto.getUsername();
        String role = userService.getRoleByUsername(username).getName();
        String name = userService.findByEmail(username).get().getName();
        String lastName = userService.findByEmail(username).get().getLastName();
        String email = userService.findByEmail(username).get().getEmail();
        Integer id = userService.findByEmail(username).get().getId();

        LoginDTOResponse response = new LoginDTOResponse(jwt, role, name, lastName, email, id);

        if (jwt != null) {
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}