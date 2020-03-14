package com.uniovi.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.entities.Friendship;
import com.uniovi.entities.User;
import com.uniovi.services.FriendshipsService;
import com.uniovi.services.UsersService;

@Controller
public class FriendsController {

	@Autowired
	UsersService usersService;
	@Autowired
	FriendshipsService friendshipsService;

	private boolean acceptedRequest = false;
	private boolean refusedRequest = false;

	// REQUESTS -------------------------------------------------------------------

	@RequestMapping("/friends/requests")
	public String getRequest(Model model, Pageable pageable, Principal principal) {
		// We load the user and its requests
		Page<Friendship> friendships = null;
		User user = usersService.getUserByEmail(principal.getName());
		friendships = friendshipsService.getRequests(pageable, user);
		// We store the list and message conditionals
		model.addAttribute("requestsList", friendships.getContent());
		model.addAttribute("page", friendships);
		model.addAttribute("acceptedRequest", acceptedRequest);
		model.addAttribute("refusedRequest", refusedRequest);
		// We restore the message conditionals
		acceptedRequest = false;
		refusedRequest = false;
		return "friends/requests";
	}

	// ACCEPT -------------------------------------------------------------------

	@RequestMapping(value = "/friends/accept/{id}")
	public String accept(Model model, Principal principal, @PathVariable Long id) {
		// We load the user and the friendship
		User user = usersService.getUserByEmail(principal.getName());
		Friendship friendship = friendshipsService.getFriendship(id);
		// And we accept the friendship if they are not friends already
		if (friendship != null && !user.isFriend(friendship.getRequester())) {
			user.acceptFriendship(friendship);
			usersService.updateUser(user);
			acceptedRequest = true;
		}
		return "redirect:/friends/requests";
	}

	// REFUSE ------------------------------------------------------------

	@RequestMapping(value = "/friends/refuse/{id}")
	public String refuse(Model model, Principal principal, @PathVariable Long id) {
		// We load the friendship
		Friendship friendship = friendshipsService.getFriendship(id);
		// And we delete it
		if (friendship != null) {
			friendshipsService.remove(friendship);
			refusedRequest = true;
		}
		return "redirect:/friends/requests";
	}

	// LIST -----------------------------------------------------------------

	@RequestMapping(value = "/friends/list")
	public String list(Model model, Pageable pageable, Principal principal) {
		// We load the user and the friend list
		Page<Friendship> friendships = null;
		User user = usersService.getUserByEmail(principal.getName());
		friendships = friendshipsService.getFriends(pageable, user);
		// We store and show the list
		model.addAttribute("friendsList", friendships.getContent());
		model.addAttribute("page", friendships);
		return "friends/list";
	}

}