package com.uniovi.repositories;

import com.uniovi.entities.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface FriendshipRepository extends CrudRepository<Friendship, Long> {

	Page<Friendship> findByRequestedAndPending(Pageable pageable, User requested, boolean pending);
	
}
