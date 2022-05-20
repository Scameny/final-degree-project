package es.udc.tfg.backend.rest.dtos;

public class UserRolDto {

	public interface AllValidations {
	}

	public interface UpdateValidations {
	}

	private Long id;
	private String userName;
	private String emailAdress;
	private String rol;

	public UserRolDto() {
	}

	public UserRolDto(Long id, String userName, String emailAdress, String rol) {
		this.id = id;
		this.userName = userName;
		this.emailAdress = emailAdress;
		this.rol = rol;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailAdress() {
		return emailAdress;
	}

	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

}