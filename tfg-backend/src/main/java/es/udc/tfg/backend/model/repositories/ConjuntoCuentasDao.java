package es.udc.tfg.backend.model.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;

import es.udc.tfg.backend.model.entities.ConjuntoCuentas;

public interface ConjuntoCuentasDao extends PagingAndSortingRepository<ConjuntoCuentas, Long> {

	Slice<ConjuntoCuentas> findAllByOrderByFechaCreacionDesc(Pageable pageable);

}
