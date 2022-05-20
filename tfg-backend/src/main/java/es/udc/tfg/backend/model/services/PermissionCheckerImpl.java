package es.udc.tfg.backend.model.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.tfg.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.tfg.backend.model.entities.User;
import es.udc.tfg.backend.model.repositories.UserDao;

@Service
@Transactional(readOnly = true)
public class PermissionCheckerImpl implements PermissionChecker {

	@Autowired
	private UserDao userDao;

	public PermissionCheckerImpl(UserDao userDao) {
		super();
		this.userDao = userDao;
	}

	@Override
	public void checkUserExists(Long userId) throws InstanceNotFoundException {

		if (!userDao.existsById(userId)) {
			throw new InstanceNotFoundException("project.entities.user", userId);
		}

	}

	@Override
	public User checkUser(Long userId) throws InstanceNotFoundException {

		Optional<User> user = userDao.findById(userId);

		if (!user.isPresent()) {
			throw new InstanceNotFoundException("project.entities.user", userId);
		}

		return user.get();

	}

}