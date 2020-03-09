package com.uniovi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Friendship;
import com.uniovi.entities.User;
import com.uniovi.repositories.FriendshipRepository;

@Service
public class FriendshipsService {

	@Autowired
	private FriendshipRepository friendshipRepository;

	public Page<Friendship> getRequests(Pageable pageable, User user) {
		Page<Friendship> friendships = friendshipRepository.findByRequestedAndPending(pageable, user, true);
		return friendships;
	}

	public Friendship getFriendship(Long id) {
		Optional<Friendship> friendship = friendshipRepository.findById(id);
		return (friendship.isPresent()) ? friendship.get() : null;
	}

}