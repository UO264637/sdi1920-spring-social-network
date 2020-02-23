package com.uniovi.services;

import com.uniovi.entities.Role;
import com.uniovi.repositories.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesService {

	public static final String[] ROLES = {"ROLE_ADMIN", "ROLE_USER"};

	@Autowired
	private RolesRepository rolesRepository;

	public void addRole(Role role) { rolesRepository.save(role); }
	public Role findByName(String name) {
		return rolesRepository.findByName(name);
	}

}
