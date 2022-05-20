package es.udc.tfg.backend.model.repositories;

import java.util.List;

import es.udc.tfg.backend.model.entities.CuentaTwitter;

public interface ExtensionCuentaTwitterDao {

	List<CuentaTwitter> busquedaPorPorcentaje(Long idConjunto, Double porcentajeBot, Double porcentajeHumano);

}
