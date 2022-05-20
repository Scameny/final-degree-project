package es.udc.tfg.backend.rest.dtos;

import java.util.List;
import java.util.stream.Collectors;

import es.udc.tfg.backend.model.entities.User;

public class UserConversor {

	private UserConversor() {
	}

	public final static UserDto toUserDto(User user) {
		return new UserDto(user.getId(), user.getUserName(), user.getPassword(), user.getFirstName(),
				user.getLastName1(), user.getLastName2(), user.getEmailAdress());
	}

	public final static User toUser(UserDto userDto) {

		return new User(userDto.getFirstName(), userDto.getFirstLastName(), userDto.getUserName(),
				userDto.getEmailAdress(), userDto.getPassword());
	}

	public final static AuthenticatedUserDto toAuthenticatedUserDto(String serviceToken, User user) {

		return new AuthenticatedUserDto(serviceToken, toUserDto(user));

	}

	public final static UserRolDto toUserRolDto(User user) {

		return new UserRolDto(user.getId(), user.getUserName(), user.getEmailAdress(), user.getRole().getName());
	}

	public final static List<UserRolDto> toUserRolDtos(List<User> users) {
		return users.stream().map(u -> toUserRolDto(u)).collect(Collectors.toList());
	}

}
