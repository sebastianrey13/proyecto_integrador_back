package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.RoleDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.UserDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Role;
import com.grupo5.AlquilerEquiposConstruccion.model.User;
import com.grupo5.AlquilerEquiposConstruccion.repository.UserRepository;
import com.grupo5.AlquilerEquiposConstruccion.service.EmailService;
import com.grupo5.AlquilerEquiposConstruccion.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j(topic = "userServiceImplLogger")
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    EmailService emailService;

    @Override
    public UserDTO saveUser(UserDTO userDTO) throws BadRequestException {
        if (userDTO.getName() == null || userDTO.getLastName() == null || userDTO.getEmail() == null || userDTO.getPhoneNumber() == null || userDTO.getPassword() == null) {
            throw new BadRequestException("The user has null values.");
        }
        Boolean isExistingUser = userRepository.findByEmail(userDTO.getEmail()).isPresent();
        User userCreated;
        if (isExistingUser) {
            throw new BadRequestException("The user already exists.");
        } else {
            userCreated = mapper.convertValue(userDTO, User.class);
            log.info("The user was created successfully.");
            userRepository.save(userCreated);
        }

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userDTO.getEmail());
        mailMessage.setSubject("Registro completo en AlquiConstruye!");
        mailMessage.setText("Bienvenido,\n\nHiciste el proceso de registro con el email: "
                + userDTO.getEmail() + ".\n\n"
                + "Para ingresar a tu cuenta haz click en el siguiente enlace: "
                + "https://www.google.com/.\n\n"
                + "Saludos desde G5 - C4 - DH.");
        emailService.sendEmail(mailMessage);

        return mapper.convertValue(userCreated, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User user:
             users) {
            usersDTO.add(mapper.convertValue(user, UserDTO.class));
        }
        return usersDTO;
    }

    @Override
    public Optional<UserDTO> getUserById(Integer id) throws NotFoundException {
        User userFounded = userRepository.findById(id).orElseThrow(() -> new NotFoundException("The " +
                "user with the id: " + id + " was not found."));
        return Optional.ofNullable(mapper.convertValue(userFounded, UserDTO.class));
    }

    @Override
    public Optional<UserDTO> findByEmail(String email) throws NotFoundException {
        User userFounded = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("The " +
                "user with the email: " + email + " was not found."));
        return Optional.ofNullable(mapper.convertValue(userFounded, UserDTO.class));
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer id) throws NotFoundException {
        Optional<UserDTO> existingUser = getUserById(id);
        if (existingUser.isPresent()) {
            UserDTO userToUpdate = existingUser.get();
            userToUpdate.setRole(userDTO.getRole());

            User userUpdated = mapper.convertValue(userToUpdate, User.class);
            userRepository.save(userUpdated);

            log.info("The user was updated successfully.");
        }
        return existingUser.orElse(null);
    }


    @Override
    public void deleteUserById(Integer id) throws NotFoundException{
        Optional<UserDTO> userFounded = getUserById(id);
        userRepository.findById(id).orElseThrow(() -> new NotFoundException("The " +
                "user with the id: " + id + " was not found."));
        userRepository.deleteById(id);
    }

    @Override
    public RoleDTO getRoleByUsername(String username) throws NotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            Role role = user.get().getRole();
            return mapper.convertValue(role, RoleDTO.class);
        }
        throw new NotFoundException("User not found.");
    }


    @Override
    public void confirmationEmail(String email) throws NotFoundException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Registro completo en AlquiConstruye!");
        mailMessage.setText("Bienvenido,\n\nHiciste el proceso de registro con el email: "
                + email + ".\n\n"
                + "Para ingresar a tu cuenta haz click en el siguiente enlace: "
                + "https://www.google.com/.\n\n"
                + "Saludos desde G5 - C4 - DH.");
        emailService.sendEmail(mailMessage);
    }
}

