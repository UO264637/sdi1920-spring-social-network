package com.uniovi.repositories;

import com.uniovi.entities.*;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);

	@Query("SELECT r FROM User r WHERE r.user = ?1 and r.role = ?2 ORDER BY r.id ASC ")
	List<User> findOtherUsers(User user, Role role);

}
