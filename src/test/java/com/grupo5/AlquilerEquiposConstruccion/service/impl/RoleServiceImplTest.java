package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.RoleDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Role;
import com.grupo5.AlquilerEquiposConstruccion.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ObjectMapper objectMapper;

    private RoleDTO sampleRoleDTO;
    private Role sampleRole;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleRoleDTO = new RoleDTO(1, "ADMIN");
        sampleRole = new Role(1, "ADMIN");

        when(objectMapper.convertValue(eq(sampleRoleDTO), eq(Role.class)))
                .thenReturn(sampleRole);
        when(objectMapper.convertValue(eq(sampleRole), eq(RoleDTO.class)))
                .thenReturn(sampleRoleDTO);
    }

    @Test
    public void testGetRoleById() throws NotFoundException {
        // Arrange
        when(roleRepository.findById(sampleRoleDTO.getId())).thenReturn(Optional.of(sampleRole));

        // Act
        Optional<RoleDTO> result = roleService.getRoleById(sampleRoleDTO.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(sampleRoleDTO.getId(), result.get().getId());
    }





}
