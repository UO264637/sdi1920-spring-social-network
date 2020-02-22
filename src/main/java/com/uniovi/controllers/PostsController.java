package com.uniovi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostsController {

	@RequestMapping("/feed")
	public String getList(){
		return "Getting Posts";
	}

	@RequestMapping("/feed/add")
	public String setMark(){
		return "Adding Post";
	}

}
