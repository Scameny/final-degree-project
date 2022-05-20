package es.udc.tfg.backend.model.services;

import java.util.List;

import es.udc.tfg.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.tfg.backend.model.entities.ConjuntoCuentas;
import es.udc.tfg.backend.model.entities.CuentaTwitter;
import es.udc.tfg.backend.model.entities.Interaccion;

public interface ConjuntoService {

	ConjuntoCuentas verDetallesConjunto(Long idConjunto) throws InstanceNotFoundException;

	List<CuentaTwitter> filtroCuentasHumanas(Long idConjunto, Double porcentajeHumano) throws InstanceNotFoundException;

	List<CuentaTwitter> filtroCuentasBot(Long idConjunto, Double porcentajeBot) throws InstanceNotFoundException;

	List<CuentaTwitter> filtroCuentas(Long idConjunto, Double porcentajeHumano, Double porcentajeBot)
			throws InstanceNotFoundException;

	Block<ConjuntoCuentas> verConjuntos(int page, int size);

	List<Interaccion> interaccionesEntreCuentas(List<String> cuentas);

}
