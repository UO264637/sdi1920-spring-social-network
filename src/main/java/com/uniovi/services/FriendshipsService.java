package com.uniovi.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(FriendshipsService.class);

	/**
	 * @param pageable
	 * @param user		
	 * @return	List of friend requests of the user
	 */
	public Page<Friendship> getRequests(Pageable pageable, User user) {
		Page<Friendship> friendships = friendshipRepository.findByRequestedAndPending(pageable, user, true);
		logger.info("The user with the email " + user.getEmail() + " has listed their friend requests");
		return friendships;
	}
	
	/**
	 * @param pageable
	 * @param user
	 * @return	List of friends of the user
	 */
	public Page<Friendship> getFriends(Pageable pageable, User user) {
		Page<Friendship> friendships = friendshipRepository.findByRequesterAndPending(pageable, user, false);
		logger.info("The user with the email " + user.getEmail() + " has listed their friends");
		return friendships;
	}

	/**
	 * @param id
	 * @return	Friendship with the specified id
	 */
	public Friendship getFriendship(Long id) {
		Optional<Friendship> friendship = friendshipRepository.findById(id);
		logger.info("Requested friendship with the id " + id);
		return (friendship.isPresent()) ? friendship.get() : null;
	}

	/**
	 * Removes a friendship with an specified user
	 * @param friendship
	 */
	public void remove(Friendship friendship) {
		friendshipRepository.delete(friendship);
		logger.info("Deleted the friendship with the id " +friendship.getId());
	}

}