package com.uniovi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	/**
	 * @return	Home page
	 */
	@RequestMapping("/" )
	public String index() {
		return "home";
	}
	
	/**
	 * @return	Admin page, just for testing
	 */
	@RequestMapping("/admin" )
	public String admin() {
		return "home";
	}

}
