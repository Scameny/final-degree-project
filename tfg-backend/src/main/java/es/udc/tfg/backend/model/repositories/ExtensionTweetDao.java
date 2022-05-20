package es.udc.tfg.backend.model.repositories;

import java.util.List;

import es.udc.tfg.backend.model.entities.Interaccion;

public interface ExtensionTweetDao {

	List<Interaccion> retweetsEntreCuentas(List<String> cuentas);

	List<Interaccion> meGustaEntreCuentas(List<String> cuentas);

}
