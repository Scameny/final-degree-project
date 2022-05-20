package es.udc.tfg.backend.model.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import es.udc.tfg.backend.model.entities.CuentaTwitter;

public interface CuentaTwitterDao extends PagingAndSortingRepository<CuentaTwitter, String>, ExtensionCuentaTwitterDao {

}
