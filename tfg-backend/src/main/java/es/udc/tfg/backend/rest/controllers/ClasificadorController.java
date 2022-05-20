package es.udc.tfg.backend.rest.controllers;

import static es.udc.tfg.backend.rest.dtos.ConjuntoDtoConversor.conjuntoToConjuntoDto;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.udc.tfg.backend.model.common.exceptions.DuplicateInstanceException;
import es.udc.tfg.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.tfg.backend.model.entities.ConjuntoCuentas;
import es.udc.tfg.backend.model.services.AlmacenamientoService;
import es.udc.tfg.backend.model.services.ClasificadorService;
import es.udc.tfg.backend.model.services.XmlParseException;
import es.udc.tfg.backend.rest.controllers.common.ErrorsDto;
import es.udc.tfg.backend.rest.dtos.ConjuntoDto;
import es.udc.tfg.backend.rest.dtos.CreacionConjuntoDto;

@RestController
@RequestMapping("/clasificacion")
public class ClasificadorController {

	private final static String BAD_FORMED_XML_EXCEPTION_CODE = "project.exceptions.XmlParseException";

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ClasificadorService clasificadorService;

	@Autowired
	private AlmacenamientoService almacenamientoService;

	@ExceptionHandler(XmlParseException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorsDto handleIncorrectPasswordException(XmlParseException exception, Locale locale) {

		String errorMessage = messageSource.getMessage(BAD_FORMED_XML_EXCEPTION_CODE, null,
				BAD_FORMED_XML_EXCEPTION_CODE, locale);

		return new ErrorsDto(errorMessage);

	}

	@PostMapping("/uploadFile")
	public ConjuntoDto subirConjuntoDeEntrada(@RequestParam("file") MultipartFile file,
			@ModelAttribute CreacionConjuntoDto creacionConjunto, @RequestAttribute Long userId)
			throws DuplicateInstanceException, InstanceNotFoundException, XmlParseException {
		String fileName = almacenamientoService.storeFile(file);

		ConjuntoCuentas conjunto = clasificadorService.clasificarConjuntoDeCuentas(fileName,
				creacionConjunto.getNombre(), creacionConjunto.getDescripcion(), userId);
		almacenamientoService.destroyFile(fileName);
		return conjuntoToConjuntoDto(conjunto);
	}

}