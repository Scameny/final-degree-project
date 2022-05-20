package es.udc.tfg.backend.model.services;

import java.util.List;

import es.udc.tfg.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.tfg.backend.model.entities.Role;

public interface RoleService {

	List<Role> consultarRoles();

	void cambiarRolUsuario(Long rolId, Long userId) throws InstanceNotFoundException;
}
