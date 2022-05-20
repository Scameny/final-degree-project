package es.udc.tfg.backend.model.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import es.udc.tfg.backend.model.entities.ResultadoClasificador;

public interface ResultadoClasificadorDao extends PagingAndSortingRepository<ResultadoClasificador, Long> {

}
