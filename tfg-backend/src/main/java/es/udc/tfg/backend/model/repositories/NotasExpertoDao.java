package es.udc.tfg.backend.model.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import es.udc.tfg.backend.model.entities.NotasExperto;

public interface NotasExpertoDao extends PagingAndSortingRepository<NotasExperto, Long> {

}
