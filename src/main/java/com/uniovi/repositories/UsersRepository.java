package com.uniovi.repositories;

import com.uniovi.entities.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);

	@Query("SELECT r FROM User r WHERE r.email != ?1 and r.role != ?2 ORDER BY r.id ASC ")
	Page<User> findOtherUsers(Pageable pageable, String email, Role role);

}
