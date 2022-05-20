package es.udc.tfg.backend.rest.controllers.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class PropiedadesFichero {
	private String archivoSubido;

	public String getArchivoSubido() {
		return archivoSubido;
	}

	public void setArchivoSubido(String archivoSubido) {
		this.archivoSubido = archivoSubido;
	}
}