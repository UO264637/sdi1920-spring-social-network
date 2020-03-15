package com.uniovi.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {
	
	@Autowired
	UsersService usersService;
	@Autowired
	RolesService rolesService;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private SignUpFormValidator signUpFormValidator;
	
	private boolean sentRequest = false;
	
	// SIGNUP ------------------------------------------------------------------------------
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "signup";
		}
		user.setRole(rolesService.findByName(RolesService.ROLES[1]));
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getConfirmPassword());
		return "home";
	}
	
	// LOGIN ------------------------------------------------------------------------------
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}
	
	// LIST ------------------------------------------------------------------------------

	@RequestMapping("/user/list")
	public String getList(Model model, Pageable pageable, Principal principal, @RequestParam(value = "", required=false) String searchText){
		Page<User> users = null;
		
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		
		if (searchText != null && !searchText.isEmpty()) {
			users = usersService.searchUserByNameSurnameAndEmail(pageable, searchText, user);
		}
		else {
			users = usersService.getOtherUsers(pageable, user);
		}
		model.addAttribute("user", user);
		model.addAttribute("userList", users.getContent());
		model.addAttribute("page", users);
		model.addAttribute("sentRequest", sentRequest);
		sentRequest = false;
		return "user/list";
	}
	
	// ADD FRIEND -----------------------------------------------------------------------------
	
	/**
	 * Sends a friend request to the specified user
	 * @param model
	 * @param principal
	 * @param email	Email of the friend to accept
	 * @return	Redirection to the list of users
	 */
	@RequestMapping(value = "/user/addFriend/{email}")
	public String addFriend(Model model, Principal principal, @PathVariable String email) {
		// We load both users
		User user = usersService.getUserByEmail(principal.getName());
		User userToAdd = usersService.getUserByEmail(email);
		// We check that the users are not admins
		if (user.getRole().getName().equals("ROLE_ADMIN") ||
				userToAdd.getRole().getName().equals("ROLE_ADMIN"))
			return "redirect:/user/list";
		// Then we trigger the friendship request
		if (!user.isFriend(userToAdd) && !user.isRequested(userToAdd)) {
			user.requestFriendship(userToAdd);
			usersService.updateUser(user);
			sentRequest = true;
		}
		return "redirect:/user/list";
	}
	

	@RequestMapping("/user/list/delete")
	public String updateList(Model model, Pageable pageable, Principal principal, @RequestParam List<String> ids) {
		
		for (String id: ids) {
			usersService.deleteUser(Long.parseLong(id));
		}
		
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		Page<User> users = usersService.getOtherUsers(pageable, user);
		model.addAttribute("userList", users.getContent());
		return "/user/list :: tableUsers";
	}
}