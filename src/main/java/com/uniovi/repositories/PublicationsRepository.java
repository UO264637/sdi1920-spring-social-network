package com.uniovi.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Publication;
import com.uniovi.entities.User;

public interface PublicationsRepository extends CrudRepository<Publication, Long> {
	
	@Query("SELECT r FROM Publication r WHERE r.user = ?1 ORDER BY r.id ASC ")
	Page<Publication> findAllByUser(Pageable pageable, User user);
	
	Page<Publication> findAll(Pageable pageable);
}
