package es.udc.tfg.backend.model.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import es.udc.tfg.backend.model.entities.Role;

public interface RoleDao extends PagingAndSortingRepository<Role, Long> {
	Role findByName(String name);

}
