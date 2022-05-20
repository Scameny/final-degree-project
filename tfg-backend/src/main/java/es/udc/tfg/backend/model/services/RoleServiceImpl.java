package es.udc.tfg.backend.model.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.tfg.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.tfg.backend.model.entities.Role;
import es.udc.tfg.backend.model.entities.User;
import es.udc.tfg.backend.model.repositories.RoleDao;
import es.udc.tfg.backend.model.repositories.UserDao;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	private final PermissionChecker permissionChecker;
	private final RoleDao roleDao;
	private final UserDao userDao;

	public RoleServiceImpl(PermissionChecker permissionChecker, RoleDao roleDao, UserDao userDao) {
		super();
		this.permissionChecker = permissionChecker;
		this.roleDao = roleDao;
		this.userDao = userDao;
	}

	@Override
	public void cambiarRolUsuario(Long rolId, Long userId) throws InstanceNotFoundException {

		User user = permissionChecker.checkUser(userId);

		user.setRole(roleDao.findById(rolId).get());
	}

	@Override
	public List<Role> consultarRoles() {
		Iterable<Role> rolesIt = roleDao.findAll();
		List<Role> rolesAsList = new ArrayList<>();

		rolesIt.forEach(r -> rolesAsList.add(r));

		return rolesAsList;

	}

}
