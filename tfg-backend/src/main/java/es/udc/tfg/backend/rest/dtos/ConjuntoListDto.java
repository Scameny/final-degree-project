package es.udc.tfg.backend.rest.dtos;

public class ConjuntoListDto {

	private Long id;
	private String nombre;
	private String autorUsername;

	public ConjuntoListDto(Long id, String nombre, String autorUsername) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.autorUsername = autorUsername;
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

}
