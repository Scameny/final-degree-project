package es.udc.tfg.backend.model.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.tfg.backend.model.common.exceptions.DuplicateInstanceException;
import es.udc.tfg.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.tfg.backend.model.entities.Role;
import es.udc.tfg.backend.model.entities.User;
import es.udc.tfg.backend.model.repositories.RoleDao;
import es.udc.tfg.backend.model.repositories.UserDao;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private PermissionChecker permissionChecker;
	@Autowired
	private PasswordEncoder passwordEncoder;
	private UserDao userDao;
	private RoleDao roleDao;

	@Autowired
	public void setUserRepository(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setPermissionChecker(PermissionChecker permissionChecker) {
		this.permissionChecker = permissionChecker;
	}

	@Autowired
	public void setUserRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public void signUp(User user) throws DuplicateInstanceException {

		if (userDao.existsByUserName(user.getUserName())) {
			throw new DuplicateInstanceException("project.entities.user", user.getUserName());
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(roleDao.findByName("USER_ROLE"));

		userDao.save(user);

	}

	@Override
	@Transactional(readOnly = true)
	public User login(String userName, String password) throws IncorrectLoginException {

		Optional<User> user = userDao.findByUserName(userName);

		if (!user.isPresent()) {
			throw new IncorrectLoginException(userName, password);
		}

		if (!passwordEncoder.matches(password, user.get().getPassword())) {
			throw new IncorrectLoginException(userName, password);
		}

		return user.get();

	}

	@Override
	@Transactional(readOnly = true)
	public User loginFromId(Long id) throws InstanceNotFoundException {
		return permissionChecker.checkUser(id);
	}

	@Override
	public User updateProfile(Long id, String name, String lastName1, String lastName2, String email)
			throws InstanceNotFoundException {

		User user = permissionChecker.checkUser(id);

		user.setFirstName(name);
		user.setLastName1(lastName1);
		if (!lastName2.isBlank())
			user.setLastName2(lastName2);
		user.setEmailAdress(email);

		return user;

	}

	@Override
	public void changePassword(Long id, String oldPassword, String newPassword)
			throws InstanceNotFoundException, IncorrectPasswordException {

		User user = permissionChecker.checkUser(id);

		if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
			throw new IncorrectPasswordException();
		} else {
			user.setPassword(passwordEncoder.encode(newPassword));
		}

	}

	@Override
	public Role getRoleByName(String roleName) throws InstanceNotFoundException {
		return roleDao.findByName(roleName);
	}

	@Override
	public Block<User> getUsers(int page, int size) {
		Slice<User> slice = userDao.findAllByOrderByUserNameAsc(PageRequest.of(page, size));
		return new Block<>(slice.getContent(), slice.hasNext());
	}

}
