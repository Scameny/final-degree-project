package es.udc.tfg.backend.model.services;

import es.udc.tfg.backend.model.common.exceptions.DuplicateInstanceException;
import es.udc.tfg.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.tfg.backend.model.entities.Role;
import es.udc.tfg.backend.model.entities.User;

public interface UserService {
	void signUp(User user) throws DuplicateInstanceException;

	User login(String userName, String password) throws IncorrectLoginException;

	User loginFromId(Long id) throws InstanceNotFoundException, InstanceNotFoundException;

	User updateProfile(Long id, String name, String lastName1, String lastName2, String email)
			throws InstanceNotFoundException;

	void changePassword(Long id, String oldPassword, String newPassword)
			throws InstanceNotFoundException, IncorrectPasswordException;

	Role getRoleByName(String roleName) throws InstanceNotFoundException;

	Block<User> getUsers(int page, int size);
}
