package es.udc.tfg.backend.model.services;

import es.udc.tfg.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.tfg.backend.model.entities.User;

public interface PermissionChecker {
	public void checkUserExists(Long userId) throws InstanceNotFoundException;

	public User checkUser(Long userId) throws InstanceNotFoundException;

}