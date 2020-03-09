package com.uniovi.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	// REQUESTS ---------------------------------------------------------------------------------
	
	@RequestMapping("/friends/requests")
	public String getRequest(Model model, Pageable pageable, Principal principal){
		Page<Friendship> friendships = null;
		
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		
		friendships = friendshipsService.getRequests(pageable, user);

		model.addAttribute("requestsList", friendships.getContent());
		model.addAttribute("page", friendships);
		return "friends/requests";
	}
}