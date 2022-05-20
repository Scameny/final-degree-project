package es.udc.tfg.backend.model.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FicheroNoEncontradoException extends RuntimeException {

	public FicheroNoEncontradoException(String message, Throwable cause) {
		super(message, cause);
	}

	public FicheroNoEncontradoException(String message) {
		super(message);
	}

}
