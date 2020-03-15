package com.uniovi.services;

import com.uniovi.entities.Role;
import com.uniovi.repositories.RolesRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesService {

	public static final String[] ROLES = {"ROLE_ADMIN", "ROLE_USER"};

	private static final Logger logger = LoggerFactory.getLogger(RolesService.class);
	
	@Autowired
	private RolesRepository rolesRepository;

	/**
	 * @param role	Role to save in the database
	 */
	public void addRole(Role role) { 
		rolesRepository.save(role); 
		logger.info("Created Role " + role.getName());
	}
	
	/**
	 * @param name
	 * @return	Role with the specified name
	 */
	public Role findByName(String name) {
		logger.info("Retrieved Role " + name);
		return rolesRepository.findByName(name);
	}

}
