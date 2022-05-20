package es.udc.tfg.backend.model.repositories;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;

import es.udc.tfg.backend.model.entities.User;

public interface UserDao extends PagingAndSortingRepository<User, Long> {

	boolean existsByUserName(String userName);

	Slice<User> findAllByOrderByUserNameAsc(Pageable pageable);

	Optional<User> findByUserName(String userName);
}
