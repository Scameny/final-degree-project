package es.udc.tfg.backend.model.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.udc.tfg.backend.model.entities.Interaccion;

public class ExtensionTweetDaoImpl implements ExtensionTweetDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Interaccion> retweetsEntreCuentas(List<String> cuentas) {
		String queryString = "SELECT NEW es.udc.tfg.backend.model.entities.Interaccion(t.usuario.nombreUsuario, r.nombreUsuario, count(t), 'Retweet')"
				+ " FROM Tweet t join t.retweet r WHERE t.usuario.nombreUsuario in :cuentas"
				+ " AND r.nombreUsuario in :cuentas AND r.nombreUsuario not like t.usuario.nombreUsuario"
				+ " group by t.usuario.nombreUsuario, r.nombreUsuario";

		Query query = entityManager.createQuery(queryString);
		query.setParameter("cuentas", cuentas);
		List<Interaccion> interacciones = query.getResultList();
		return interacciones;
	}

	@Override
	public List<Interaccion> meGustaEntreCuentas(List<String> cuentas) {
		String queryString = "SELECT NEW es.udc.tfg.backend.model.entities.Interaccion(t.usuario.nombreUsuario, m.nombreUsuario, count(t), 'MeGusta')"
				+ " FROM Tweet t join t.meGusta m WHERE t.usuario.nombreUsuario in :cuentas"
				+ " AND m.nombreUsuario in :cuentas AND m.nombreUsuario not like t.usuario.nombreUsuario"
				+ " group by t.usuario.nombreUsuario, m.nombreUsuario";

		Query query = entityManager.createQuery(queryString);
		query.setParameter("cuentas", cuentas);
		List<Interaccion> interacciones = query.getResultList();
		return interacciones;
	}

}
