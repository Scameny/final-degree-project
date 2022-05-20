package es.udc.tfg.backend.model.services;

@SuppressWarnings("serial")
public class AlmacenamientoException extends RuntimeException {

	public AlmacenamientoException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlmacenamientoException(String message) {
		super(message);
	}

}
