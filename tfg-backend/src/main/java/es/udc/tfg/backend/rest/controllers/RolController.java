package es.udc.tfg.backend.rest.controllers;

import static es.udc.tfg.backend.rest.dtos.UserConversor.toUserRolDtos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.udc.tfg.backend.model.common.exceptions.DuplicateInstanceException;
import es.udc.tfg.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.tfg.backend.model.entities.User;
import es.udc.tfg.backend.model.services.Block;
import es.udc.tfg.backend.model.services.RoleService;
import es.udc.tfg.backend.model.services.UserService;
import es.udc.tfg.backend.rest.controllers.common.JwtGenerator;
import es.udc.tfg.backend.rest.dtos.BlockDto;
import es.udc.tfg.backend.rest.dtos.UserRolDto;

@RestController
@RequestMapping("/rol")
public class RolController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private JwtGenerator jwtGenerator;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService rolService;

	@GetMapping("/users")
	public BlockDto<UserRolDto> getUsers(@RequestParam(defaultValue = "0") int page)
			throws DuplicateInstanceException, InstanceNotFoundException {

		Block<User> block = userService.getUsers(page, 10);
		return new BlockDto<>(toUserRolDtos(block.getItems()), block.getExistMoreItems());

	}

	@PostMapping("/user/{id}")
	public void changeRolFromUser(@PathVariable("id") Long userId, @RequestParam Long rolId)
			throws InstanceNotFoundException {
		rolService.cambiarRolUsuario(rolId, userId);
	}
}
