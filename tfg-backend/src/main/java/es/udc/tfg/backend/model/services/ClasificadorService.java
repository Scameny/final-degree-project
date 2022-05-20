package es.udc.tfg.backend.model.services;

import es.udc.tfg.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.tfg.backend.model.entities.ConjuntoCuentas;

public interface ClasificadorService {

	ConjuntoCuentas clasificarConjuntoDeCuentas(String filePath, String nombreConjunto, String descripcion, Long userId)
			throws XmlParseException, InstanceNotFoundException;
}
