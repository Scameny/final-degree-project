package es.udc.tfg.backend.rest.controllers;

import static es.udc.tfg.backend.rest.dtos.ConjuntoDtoConversor.conjuntoToConjuntoCuentas;
import static es.udc.tfg.backend.rest.dtos.ConjuntoDtoConversor.conjuntoToConjuntoDto;
import static es.udc.tfg.backend.rest.dtos.ConjuntoDtoConversor.conjuntosToConjuntosListDtos;
import static es.udc.tfg.backend.rest.dtos.ConjuntoDtoConversor.cuentasToCuentasDto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.udc.tfg.backend.model.common.exceptions.DuplicateInstanceException;
import es.udc.tfg.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.tfg.backend.model.entities.ConjuntoCuentas;
import es.udc.tfg.backend.model.entities.CuentaTwitter;
import es.udc.tfg.backend.model.entities.Interaccion;
import es.udc.tfg.backend.model.services.Block;
import es.udc.tfg.backend.model.services.ConjuntoService;
import es.udc.tfg.backend.rest.dtos.BlockDto;
import es.udc.tfg.backend.rest.dtos.ConjuntoDto;
import es.udc.tfg.backend.rest.dtos.ConjuntoListDto;
import es.udc.tfg.backend.rest.dtos.CuentaDto;

@RestController
@RequestMapping("/conjunto")
public class ConjuntoController {

	@Autowired
	private ConjuntoService conjuntoService;

	@GetMapping("/lista")
	public BlockDto<ConjuntoListDto> verConjuntos(@RequestParam(defaultValue = "0") int page) {
		Block<ConjuntoCuentas> block = conjuntoService.verConjuntos(page, 10);
		return new BlockDto<>(conjuntosToConjuntosListDtos(block.getItems()), block.getExistMoreItems());
	}

	@GetMapping("/{id}")
	public ConjuntoDto verDetalleConjunto(@PathVariable("id") Long id, @RequestAttribute Long userId)
			throws DuplicateInstanceException, InstanceNotFoundException {

		return conjuntoToConjuntoDto(conjuntoService.verDetallesConjunto(id));
	}

	@GetMapping("/{id}/cuentas")
	public List<CuentaDto> verCuentasConjunto(@PathVariable("id") Long id, @RequestAttribute Long userId)
			throws DuplicateInstanceException, InstanceNotFoundException {

		return conjuntoToConjuntoCuentas(conjuntoService.verDetallesConjunto(id));
	}

	@GetMapping("/{id}/cuentas/filtro")
	public List<CuentaDto> verCuentasConFiltro(@PathVariable("id") Long id,
			@RequestParam(required = false) Double porcentajeHumano,
			@RequestParam(required = false) Double porcentajeBot)
			throws DuplicateInstanceException, InstanceNotFoundException {
		if (porcentajeHumano == null) {
			return cuentasToCuentasDto(conjuntoService.filtroCuentasBot(id, porcentajeBot), id);
		} else if (porcentajeBot == null) {
			return cuentasToCuentasDto(conjuntoService.filtroCuentasHumanas(id, porcentajeHumano), id);
		}
		return cuentasToCuentasDto(conjuntoService.filtroCuentas(id, porcentajeBot, porcentajeHumano), id);
	}

	@GetMapping("/{id}/interacciones")
	public List<Interaccion> getInteracciones(@PathVariable("id") Long id) throws InstanceNotFoundException {

		List<CuentaTwitter> cuentas = conjuntoService.verDetallesConjunto(id).getCuentas();
		List<Interaccion> interacciones = conjuntoService.interaccionesEntreCuentas(
				cuentas.stream().map(c -> c.getNombreUsuario()).collect(Collectors.toList()));
		return interacciones;
	}

}