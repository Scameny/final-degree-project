package es.udc.tfg.backend.model.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.tfg.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.tfg.backend.model.entities.ConjuntoCuentas;
import es.udc.tfg.backend.model.entities.CuentaTwitter;
import es.udc.tfg.backend.model.entities.Interaccion;
import es.udc.tfg.backend.model.repositories.ConjuntoCuentasDao;
import es.udc.tfg.backend.model.repositories.CuentaTwitterDao;
import es.udc.tfg.backend.model.repositories.ResultadoClasificadorDao;
import es.udc.tfg.backend.model.repositories.TweetDao;

@Service
@Transactional
public class ConjuntoServiceImpl implements ConjuntoService {
	private final TweetDao tweetDao;
	private final ResultadoClasificadorDao resultadoDao;
	private final CuentaTwitterDao cuentaTwitterDao;
	private final ConjuntoCuentasDao conjuntoDao;
	private final PermissionChecker permissionChecker;

	public ConjuntoServiceImpl(TweetDao tweetDao, CuentaTwitterDao cuentaTwitterDao, ConjuntoCuentasDao conjuntoDao,
			PermissionChecker permissionChecker, ResultadoClasificadorDao resultadoDao) {
		super();
		this.tweetDao = tweetDao;
		this.cuentaTwitterDao = cuentaTwitterDao;
		this.conjuntoDao = conjuntoDao;
		this.permissionChecker = permissionChecker;
		this.resultadoDao = resultadoDao;
	}

	@Override
	public ConjuntoCuentas verDetallesConjunto(Long idConjunto) throws InstanceNotFoundException {
		Optional<ConjuntoCuentas> conjuntoOpt = conjuntoDao.findById(idConjunto);
		if (conjuntoOpt.isEmpty()) {
			throw new InstanceNotFoundException("project.entities.conjunto", idConjunto);
		}

		return conjuntoOpt.get();
	}

	@Override
	public List<CuentaTwitter> filtroCuentasHumanas(Long idConjunto, Double porcentajeHumano)
			throws InstanceNotFoundException {
		return cuentaTwitterDao.busquedaPorPorcentaje(idConjunto, null, porcentajeHumano);
	}

	@Override
	public List<CuentaTwitter> filtroCuentasBot(Long idConjunto, Double porcentajeBot)
			throws InstanceNotFoundException {
		return cuentaTwitterDao.busquedaPorPorcentaje(idConjunto, porcentajeBot, null);
	}

	@Override
	public List<CuentaTwitter> filtroCuentas(Long idConjunto, Double porcentajeHumano, Double porcentajeBot)
			throws InstanceNotFoundException {
		return cuentaTwitterDao.busquedaPorPorcentaje(idConjunto, porcentajeBot, porcentajeHumano);
	}

	@Override
	public Block<ConjuntoCuentas> verConjuntos(int page, int size) {
		Slice<ConjuntoCuentas> slice = conjuntoDao.findAllByOrderByFechaCreacionDesc(PageRequest.of(page, size));
		return new Block<>(slice.getContent(), slice.hasNext());
	}

	@Override
	public List<Interaccion> interaccionesEntreCuentas(List<String> cuentas) {
		List<Interaccion> interacciones = tweetDao.retweetsEntreCuentas(cuentas);
		interacciones.addAll(tweetDao.meGustaEntreCuentas(cuentas));
		return interacciones;
	}

}
