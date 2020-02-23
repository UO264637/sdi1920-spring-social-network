package com.uniovi.repositories;

import com.uniovi.entities.*;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);

	//Page<User> findOtherUsers(String id, Pageable pageable);
	//TODO

}
