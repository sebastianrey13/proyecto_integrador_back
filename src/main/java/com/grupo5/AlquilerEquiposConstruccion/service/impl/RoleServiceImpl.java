package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.RoleDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Role;
import com.grupo5.AlquilerEquiposConstruccion.repository.RoleRepository;
import com.grupo5.AlquilerEquiposConstruccion.service.RoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class RoleServiceImpl implements RoleService {

    private final Logger logger = Logger.getLogger(CategoryServiceImpl.class);

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ObjectMapper mapper;

    @Override
    public List<Role> getAllRoles() {
        return null;
    }

    @Override
    public Optional<RoleDTO> getRoleById(Integer id) throws NotFoundException{
        Role roleFounded = roleRepository.findById(id).orElseThrow(() -> new NotFoundException("The " +
                "role with the id: " + id + " was not found."));
        return Optional.ofNullable(mapper.convertValue(roleFounded, RoleDTO.class));
    }

    @Override
    public Role saveRole(Role role) {
        return null;
    }

    @Override
    public Role updateRole(Role role) {
        return null;
    }

    @Override
    public void deleteRoleById(Integer id) {

    }
}
