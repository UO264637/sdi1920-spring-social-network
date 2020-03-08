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

	@Query("SELECT r FROM User r WHERE (LOWER(r.email) LIKE LOWER(?1) OR LOWER(r.name) LIKE LOWER(?1) OR LOWER(r.surname) LIKE LOWER(?1))"
			+ " AND (r.email != ?2 and r.role != ?3) ORDER BY r.id ASC")
	Page<User> searchByNameSurnameAndEmail(Pageable pageable, String searchtext, String email, Role role);
}
