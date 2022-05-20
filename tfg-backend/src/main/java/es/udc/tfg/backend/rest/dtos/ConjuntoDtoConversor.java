package es.udc.tfg.backend.rest.dtos;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import es.udc.tfg.backend.model.entities.ConjuntoCuentas;
import es.udc.tfg.backend.model.entities.CuentaTwitter;
import es.udc.tfg.backend.model.entities.ResultadoClasificador;

public class ConjuntoDtoConversor {

	public final static ConjuntoDto conjuntoToConjuntoDto(ConjuntoCuentas conjunto) {
		return new ConjuntoDto(conjunto.getId(), conjunto.getNombre(), conjunto.getDescripcion(),
				conjunto.getAutor().getUserName(), conjunto.getFechaCreacion());
	}

	public final static ConjuntoListDto conjuntoToConjuntoListDto(ConjuntoCuentas conjunto) {
		return new ConjuntoListDto(conjunto.getId(), conjunto.getNombre(), conjunto.getAutor().getUserName());
	}

	public final static List<ConjuntoListDto> conjuntosToConjuntosListDtos(List<ConjuntoCuentas> conjuntos) {
		return conjuntos.stream().map(c -> conjuntoToConjuntoListDto(c)).collect(Collectors.toList());
	}

	public final static CuentaDto cuentaToCuentaDto(CuentaTwitter cuenta, Long idConjunto) {
		Optional<ResultadoClasificador> resultado = cuenta.getResultadosClasificador().stream()
				.filter(r -> r.getConjunto().getId() == idConjunto).findFirst();
		if (resultado.isEmpty())
			return new CuentaDto(cuenta.getNombreUsuario(), (double) 0, (double) 0);
		return new CuentaDto(cuenta.getNombreUsuario(), resultado.get().getPorcentajeBot(),
				resultado.get().getPorcentajeHumano());
	}

	public final static List<CuentaDto> cuentasToCuentasDto(List<CuentaTwitter> cuentas, Long idConjunto) {
		List<CuentaDto> l = cuentas.stream().map(c -> cuentaToCuentaDto(c, idConjunto)).collect(Collectors.toList());
		Collections.sort(l);
		return l;
	}

	public final static List<CuentaDto> conjuntoToConjuntoCuentas(ConjuntoCuentas conjunto) {

		List<CuentaDto> l = conjunto.getCuentas().stream().map(c -> cuentaToCuentaDto(c, conjunto.getId()))
				.collect(Collectors.toList());
		Collections.sort(l);
		return l;

	}
}
