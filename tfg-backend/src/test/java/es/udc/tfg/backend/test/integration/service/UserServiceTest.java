package es.udc.tfg.backend.test.integration.service;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import es.udc.tfg.backend.model.common.exceptions.DuplicateInstanceException;
import es.udc.tfg.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.tfg.backend.model.entities.Role;
import es.udc.tfg.backend.model.entities.User;
import es.udc.tfg.backend.model.repositories.RoleDao;
import es.udc.tfg.backend.model.services.IncorrectLoginException;
import es.udc.tfg.backend.model.services.IncorrectPasswordException;
import es.udc.tfg.backend.model.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserServiceTest {

	private final Long NON_EXISTENT_ID = -1L;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleDao roleDao;

	private User createUser(String userName) {
		return new User("name", "lastName", userName, userName + "@" + userName + ".com", "password");
	}

	@BeforeEach
	public void init() {
		roleDao.save(new Role("USER"));
	}

	@Test
	public void testSignUpAndLoginFromId() throws DuplicateInstanceException, InstanceNotFoundException {

		User user = createUser("user");

		userService.signUp(user);

		User loggedInUser = userService.loginFromId(user.getId());

		assertEquals(user, loggedInUser);
		assertEquals(roleDao.findByName("USER"), user.getRole());

	}

	@Test
	public void testSignUpDuplicatedUserName() throws DuplicateInstanceException {

		Assertions.assertThrows(DuplicateInstanceException.class, () -> {
			User user = createUser("user");

			userService.signUp(user);
			userService.signUp(user);

		});

	}

	@Test
	public void testloginFromNonExistentId() throws InstanceNotFoundException {
		Assertions.assertThrows(InstanceNotFoundException.class, () -> {
			userService.loginFromId(NON_EXISTENT_ID);

		});
	}

	@Test
	public void testLogin() throws DuplicateInstanceException, IncorrectLoginException {

		User user = createUser("user");
		String clearPassword = user.getPassword();

		userService.signUp(user);

		User loggedInUser = userService.login(user.getUserName(), clearPassword);

		assertEquals(user, loggedInUser);

	}

	@Test
	public void testLoginWithIncorrectPassword() throws DuplicateInstanceException, IncorrectLoginException {

		Assertions.assertThrows(IncorrectLoginException.class, () -> {
			User user = createUser("user");
			String clearPassword = user.getPassword();

			userService.signUp(user);
			userService.login(user.getUserName(), 'X' + clearPassword);
			userService.loginFromId(NON_EXISTENT_ID);

		});

	}

	@Test
	public void testLoginWithNonExistentUserName() throws IncorrectLoginException {
		Assertions.assertThrows(IncorrectLoginException.class, () -> {
			userService.login("X", "Y");

		});

	}

	@Test
	public void testUpdateProfile() throws InstanceNotFoundException, DuplicateInstanceException {

		User user = createUser("user");

		userService.signUp(user);

		user.setFirstName('X' + user.getFirstName());
		user.setLastName1('X' + user.getLastName1());
		user.setLastName2('X' + user.getLastName2());
		user.setEmailAdress('X' + user.getEmailAdress());

		userService.updateProfile(user.getId(), 'X' + user.getFirstName(), 'X' + user.getLastName1(),
				'X' + user.getLastName2(), 'X' + user.getEmailAdress());

		User updatedUser = userService.loginFromId(user.getId());

		assertEquals(user, updatedUser);

	}

	@Test
	public void testUpdateProfileWithNonExistentId() throws InstanceNotFoundException {
		Assertions.assertThrows(InstanceNotFoundException.class, () -> {
			userService.updateProfile(NON_EXISTENT_ID, "X", "X", "X", "X");
		});

	}

	@Test
	public void testChangePassword() throws DuplicateInstanceException, InstanceNotFoundException,
			IncorrectPasswordException, IncorrectLoginException {

		User user = createUser("user");
		String oldPassword = user.getPassword();
		String newPassword = 'X' + oldPassword;

		userService.signUp(user);
		userService.changePassword(user.getId(), oldPassword, newPassword);
		userService.login(user.getUserName(), newPassword);

	}

	@Test
	public void testChangePasswordWithNonExistentId() throws InstanceNotFoundException, IncorrectPasswordException {
		Assertions.assertThrows(InstanceNotFoundException.class, () -> {
			userService.changePassword(NON_EXISTENT_ID, "X", "Y");
		});
	}

	@Test
	public void testChangePasswordWithIncorrectPassword()
			throws DuplicateInstanceException, InstanceNotFoundException, IncorrectPasswordException {
		Assertions.assertThrows(IncorrectPasswordException.class, () -> {
			User user = createUser("user");
			String oldPassword = user.getPassword();
			String newPassword = 'X' + oldPassword;

			userService.signUp(user);
			userService.changePassword(user.getId(), 'Y' + oldPassword, newPassword);
		});

	}

}
