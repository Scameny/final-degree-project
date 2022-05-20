package es.udc.tfg.backend.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Usuario")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre")
	private String firstName;

	@Column(name = "apellido1")
	private String lastName1;

	@Column(name = "apellido2")
	private String lastName2;

	@Column(name = "nombreUsuario")
	private String userName;

	@Column(name = "direccionCorreo")
	private String emailAdress;

	private String password;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "idRol")
	private Role role;

	public User(String name, String lastName1, String userName, String mailAdress, String password) {
		super();
		this.firstName = name;
		this.lastName1 = lastName1;
		this.userName = userName;
		this.emailAdress = mailAdress;
		this.password = password;
	}

	public User() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String name) {
		this.firstName = name;
	}

	public String getLastName1() {
		return lastName1;
	}

	public void setLastName1(String lastName1) {
		this.lastName1 = lastName1;
	}

	public String getLastName2() {
		return lastName2;
	}

	public void setLastName2(String lastName2) {
		this.lastName2 = lastName2;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role rol) {
		this.role = rol;
	}

}