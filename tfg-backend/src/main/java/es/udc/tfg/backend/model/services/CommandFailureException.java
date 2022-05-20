package es.udc.tfg.backend.model.services;

public class CommandFailureException extends Exception {

	public CommandFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommandFailureException(String message) {
		super(message);
	}

}
