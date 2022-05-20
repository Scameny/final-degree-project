package es.udc.tfg.backend.model.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Conjunto")
public class ConjuntoCuentas {

	private Long id;
	private String nombre;
	private String descripcion;
	private User autor;
	private LocalDateTime fechaCreacion;
	private List<CuentaTwitter> cuentas;

	public ConjuntoCuentas() {
		super();
		cuentas = new ArrayList<CuentaTwitter>();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "idAutor")
	public User getAutor() {
		return autor;
	}

	public void setAutor(User autor) {
		this.autor = autor;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@ManyToMany
	@JoinTable(name = "ConjuntoCuentas", joinColumns = { @JoinColumn(name = "idConjunto") }, inverseJoinColumns = {
			@JoinColumn(name = "nombreUsuario") })
	public List<CuentaTwitter> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<CuentaTwitter> cuentas) {
		this.cuentas = cuentas;
	}

}
