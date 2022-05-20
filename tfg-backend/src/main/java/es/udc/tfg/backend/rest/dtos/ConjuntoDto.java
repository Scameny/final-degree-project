package es.udc.tfg.backend.rest.dtos;

import java.time.LocalDateTime;

public class ConjuntoDto {

	private Long id;
	private String nombre;
	private String descripcion;
	private String autorUsername;
	private LocalDateTime fechaCreacion;

	public ConjuntoDto(Long id, String nombre, String descripcion, String autorUsername, LocalDateTime fechaCreacion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.autorUsername = autorUsername;
		this.fechaCreacion = fechaCreacion;
	}

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

	public String getAutorUsername() {
		return autorUsername;
	}

	public void setAutorUsername(String autorUsername) {
		this.autorUsername = autorUsername;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
