package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.CityDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.RoleDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.UserDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.City;
import com.grupo5.AlquilerEquiposConstruccion.model.Role;
import com.grupo5.AlquilerEquiposConstruccion.model.User;
import com.grupo5.AlquilerEquiposConstruccion.repository.UserRepository;
import com.grupo5.AlquilerEquiposConstruccion.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private ObjectMapper objectMapper;

    private CityDTO sampleCityDTO;
    private RoleDTO sampleRoleDTO;
    private UserDTO sampleUserDTO;
    private City sampleCity;
    private Role sampleRole;
    private User sampleUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleCityDTO = new CityDTO(1, "Sample City");
        sampleRoleDTO = new RoleDTO(1,"ADMIN");
        sampleUserDTO = new UserDTO(1,
                "John",
                "Doe",
                "john.doe@example.com",
                "123456789",
                "password123",
                true,
                sampleCityDTO,
                sampleRoleDTO);
        sampleCity = new City(1, "Sample City");
        sampleRole = new Role(1,"ADMIN");
        sampleUser = new User(1,
                "John",
                "Doe",
                "john.doe@example.com",
                "123456789",
                "password123",
                true,
                sampleCity,
                sampleRole);

        when(objectMapper.convertValue(eq(sampleUserDTO), eq(User.class)))
                .thenReturn(sampleUser);
        when(objectMapper.convertValue(eq(sampleUser), eq(UserDTO.class)))
                .thenReturn(sampleUserDTO);
        when(objectMapper.convertValue(eq(sampleRole), eq(RoleDTO.class)))
                .thenReturn(sampleRoleDTO);
    }

    @Test
    public void testSaveUser() throws BadRequestException {

        // Arrange
        when(userRepository.findByEmail(sampleUserDTO.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(Mockito.any(User.class))).thenReturn(sampleUser);


        // Act
        UserDTO result = userService.saveUser(sampleUserDTO);


        // Assert
        assertNotNull(result);
        assertEquals(sampleUserDTO.getId(), result.getId());
        assertEquals(sampleUserDTO.getName(), result.getName());
        assertEquals(sampleUserDTO.getLastName(), result.getLastName());
        assertEquals(sampleUserDTO.getEmail(), result.getEmail());
        assertEquals(sampleUserDTO.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(sampleUserDTO.getPassword(), result.getPassword());
        assertTrue(result.isEnabled());
        assertNotNull(result.getCity());
        assertEquals(sampleUserDTO.getCity().getId(), result.getCity().getId());
        assertEquals(sampleUserDTO.getCity().getName(), result.getCity().getName());
        assertNotNull(result.getRole());
        assertEquals(sampleUserDTO.getRole().getId(), result.getRole().getId());
        assertEquals(sampleUserDTO.getRole().getName(), result.getRole().getName());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();
        assertNotNull(capturedUser);
        assertEquals(sampleUserDTO.getName(), capturedUser.getName());


        ArgumentCaptor<SimpleMailMessage> mailMessageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(emailService).sendEmail(mailMessageCaptor.capture());
        SimpleMailMessage capturedMailMessage = mailMessageCaptor.getValue();
        assertNotNull(capturedMailMessage);
        assertEquals(sampleUserDTO.getEmail(), capturedMailMessage.getTo()[0]);

    }

    @Test
    public void testGetAllUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(List.of(sampleUser));

        // Act
        List<UserDTO> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(sampleUserDTO.getId(), result.get(0).getId());
    }

    @Test
    public void testGetUserById() throws NotFoundException {
        // Arrange
        when(userRepository.findById(sampleUserDTO.getId())).thenReturn(Optional.of(sampleUser));

        // Act
        Optional<UserDTO> result = userService.getUserById(sampleUserDTO.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(sampleUserDTO.getId(), result.get().getId());
    }

    @Test
    public void testFindByEmail() throws NotFoundException {
        // Arrange
        when(userRepository.findByEmail(sampleUserDTO.getEmail())).thenReturn(Optional.of(sampleUser));

        // Act
        Optional<UserDTO> result = userService.findByEmail(sampleUserDTO.getEmail());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(sampleUserDTO.getEmail(), result.get().getEmail());
    }

    @Test
    public void testUpdateUser() throws NotFoundException {
        // Arrange
        when(userRepository.findById(sampleUserDTO.getId())).thenReturn(Optional.of(sampleUser));
        when(userRepository.save(Mockito.any(User.class))).thenReturn(sampleUser);
        when(objectMapper.convertValue(eq(sampleUserDTO), eq(User.class)))
                .thenReturn(sampleUser);

        // Act
        UserDTO result = userService.updateUser(sampleUserDTO, sampleUserDTO.getId());

        // Assert
        assertNotNull(result);
        assertEquals(sampleUserDTO.getId(), result.getId());

        verify(userRepository, times(1)).findById(sampleUserDTO.getId());
        verify(userRepository, times(1)).save(Mockito.any(User.class));
    }



    @Test
    public void testDeleteUserById() throws NotFoundException {
        // Arrange
        when(userRepository.findById(sampleUserDTO.getId())).thenReturn(Optional.of(sampleUser));

        // Act
        assertDoesNotThrow(() -> userService.deleteUserById(sampleUserDTO.getId()));

        // Assert
        verify(userRepository, times(1)).deleteById(sampleUserDTO.getId());
    }

    @Test
    public void testGetRoleByUsername() throws NotFoundException {
        // Arrange
        when(userRepository.findByEmail(sampleUserDTO.getEmail())).thenReturn(Optional.of(sampleUser));

        // Act
        RoleDTO result = userService.getRoleByUsername(sampleUserDTO.getEmail());

        // Assert
        assertNotNull(result);
        assertEquals(sampleRoleDTO.getId(), result.getId());

        verify(userRepository, times(1)).findByEmail(sampleUserDTO.getEmail());
    }


}
