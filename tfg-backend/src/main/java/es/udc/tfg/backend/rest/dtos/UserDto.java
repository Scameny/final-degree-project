package es.udc.tfg.backend.rest.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto {

	public interface AllValidations {
	}

	public interface UpdateValidations {
	}

	private Long id;
	private String userName;
	private String password;
	private String firstName;
	private String firstLastName;
	private String secondLastName;
	private String emailAdress;

	public UserDto() {
	}

	public UserDto(Long id, String userName, String password, String firstName, String firstLastName,
			String secondLastName, String emailAdress) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.firstLastName = firstLastName;
		this.secondLastName = secondLastName;
		this.emailAdress = emailAdress;
	}

	@NotNull(groups = { AllValidations.class })
	@Size(min = 1, max = 60, groups = { AllValidations.class })
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName.trim();
	}

	@NotNull(groups = { AllValidations.class })
	@Size(min = 1, max = 60, groups = { AllValidations.class })
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotNull(groups = { AllValidations.class, UpdateValidations.class })
	@Size(min = 1, max = 60, groups = { AllValidations.class, UpdateValidations.class })
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName.trim();
	}

	@NotNull(groups = { AllValidations.class, UpdateValidations.class })
	@Size(min = 1, max = 60, groups = { AllValidations.class, UpdateValidations.class })
	public String getEmailAdress() {
		return emailAdress;
	}

	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress.trim();
	}

	@NotNull(groups = { AllValidations.class, UpdateValidations.class })
	@Size(min = 1, max = 60, groups = { AllValidations.class, UpdateValidations.class })
	public String getFirstLastName() {
		return firstLastName;
	}

	public void setFirstLastName(String firstLastName) {
		this.firstLastName = firstLastName;
	}

	@Size(max = 60, groups = { AllValidations.class, UpdateValidations.class })
	public String getSecondLastName() {
		return secondLastName;
	}

	public void setSecondLastName(String secondLastName) {
		this.secondLastName = secondLastName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
