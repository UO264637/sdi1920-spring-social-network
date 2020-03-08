package com.uniovi.controllers;

import java.security.Principal;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.uniovi.entities.Publication;
import com.uniovi.entities.User;
import com.uniovi.services.PublicationsService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.PublicationsValidator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@Controller
public class PublicationsController {

	@Autowired // Inyectar el servicio
	private PublicationsService publicationsService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private PublicationsValidator publicationsValidator;

	@RequestMapping("/publication/list")
	public String getList(Model model, Pageable pageable, Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		Page<Publication> publications = new PageImpl<Publication>(new LinkedList<Publication>());
		
		model.addAttribute("publicationList", publications.getContent());
		model.addAttribute("page", publications);
		return "publication/list";
	}

	@RequestMapping(value = "/publication/add", method = RequestMethod.POST)
	public String setPublication(Principal principal, @Validated Publication publication, BindingResult result) {
		publicationsValidator.validate(publication, result);
		if (result.hasErrors()) {
			return "/publication/add";
		}
		User user = usersService.getUserByEmail(principal.getName());
		
		publication.setUser(user);
		publication.setDate(new Date());
		
		publicationsService.addPublication(publication);
		return "redirect:/publication/list";
	}

	@RequestMapping("/publication/details/{id}")
	public String getDetail(Model model, @PathVariable Long id) {
		model.addAttribute("publication", publicationsService.getPublication(id));
		return "publication/details";

	}

	@RequestMapping("/publication/delete/{id}")
	public String deletePublication(@PathVariable Long id) {
		publicationsService.deletePublication(id);
		return "redirect:/publication/list";
	}

	@RequestMapping(value = "/publication/add")
	public String getPublication(Model model, Pageable pageable) {
		model.addAttribute("publication", new Publication());
		return "publication/add";
	}
}
