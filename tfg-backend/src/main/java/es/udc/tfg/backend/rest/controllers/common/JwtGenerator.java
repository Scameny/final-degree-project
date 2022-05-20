package es.udc.tfg.backend.rest.controllers.common;

public interface JwtGenerator {

	String generate(JwtInfo info);

	JwtInfo getInfo(String token);

}
