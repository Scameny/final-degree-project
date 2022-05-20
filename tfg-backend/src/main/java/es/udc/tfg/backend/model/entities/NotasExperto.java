package es.udc.tfg.backend.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "NotasExperto")
public class NotasExperto {

	private Long id;
	private CuentaTwitter cuenta;
	private User usuario;
	private String notas;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "idUsuario")
	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "nombreUsuario")
	public CuentaTwitter getCuenta() {
		return cuenta;
	}

	public void setCuenta(CuentaTwitter cuenta) {
		this.cuenta = cuenta;
	}
}
