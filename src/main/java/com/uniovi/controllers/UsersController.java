package com.uniovi.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	private SecurityService securityService;
	
	@Autowired
	private SignUpFormValidator signUpFormValidator;
	
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
		//user.setRole(RolesService.ROLES[0]);
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getConfirmPassword());
		return "home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}
	
	@RequestMapping("/user/list")
	public String getList(Model model, Principal principal){
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		
		model.addAttribute("userList", usersService.getOtherUsers(user));
		return "user/list";
	}
}
