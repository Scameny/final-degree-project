package es.udc.tfg.backend.model.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.udc.tfg.backend.model.entities.CuentaTwitter;

public class ExtensionCuentaTwitterDaoImpl implements ExtensionCuentaTwitterDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<CuentaTwitter> busquedaPorPorcentaje(Long idConjunto, Double porcentajeBot, Double porcentajeHumano) {

		String queryString = "SELECT distinct c FROM CuentaTwitter c join c.resultadosClasificador r WHERE r.conjunto.id  = :idConjunto";

		if (porcentajeBot != null) {
			queryString += " AND ((r.porcentajeBot IS NOT NULL AND r.porcentajeBot >= :porcentajeBot)";
		}

		if (porcentajeBot != null && porcentajeHumano != null) {
			queryString += " OR";
		} else if (porcentajeHumano != null) {
			queryString += " AND";
		}

		if (porcentajeHumano != null) {
			queryString += " (r.porcentajeHumano IS NOT NULL AND r.porcentajeHumano >= :porcentajeHumano)";
		}

		if (porcentajeBot != null)
			queryString += ")";

		Query query = entityManager.createQuery(queryString);

		query.setParameter("idConjunto", idConjunto);

		if (porcentajeBot != null) {
			query.setParameter("porcentajeBot", porcentajeBot);
		}

		if (porcentajeHumano != null) {
			query.setParameter("porcentajeHumano", porcentajeHumano);
		}

		List<CuentaTwitter> cuentas = query.getResultList();

		return cuentas;
	}

}
