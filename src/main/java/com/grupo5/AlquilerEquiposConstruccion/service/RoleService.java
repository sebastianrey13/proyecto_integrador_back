package com.grupo5.AlquilerEquiposConstruccion.service;

import com.grupo5.AlquilerEquiposConstruccion.dto.RoleDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getAllRoles();
    Optional<RoleDTO> getRoleById(Integer id) throws NotFoundException;
    Role saveRole(Role role);
    Role updateRole(Role role);
    void deleteRoleById(Integer id);
}
