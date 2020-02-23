package com.uniovi.services;

import com.uniovi.entities.Role;
import com.uniovi.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class InserDataSampleService {

	@Autowired
	private RolesService rolesService;
	@Autowired
	private UsersService usersService;

	@PostConstruct
	public void init() {
		// Roles creation
		Role admin = new Role(RolesService.ROLES[0]);
		Role standard = new Role(RolesService.ROLES[1]);
		rolesService.addRole(admin);
		rolesService.addRole(standard);

		// Users creation
		User userAdmin = new User("admin@email.com", "Admin", "Admin");
		userAdmin.setRole(admin);
		User user1 = new User("rachel@friends.com", "Rachel", "Green");
		user1.setRole(standard);
		User user2 = new User("chandler@friends.com", "Chandler", "Bing");
		user2.setRole(standard);
		User user3 = new User("monica@friends.com", "Monica", "Geller");
		user3.setRole(standard);
		User user4 = new User("phoebe@friends.com", "Phoebe", "Buffet");
		user4.setRole(standard);
		User user5 = new User("joey@friends.com", "Joey", "Tribbiani");
		user5.setRole(standard);
		User user6 = new User("ross@friends.com", "Ross", "Geller");
		user6.setRole(standard);
		usersService.addUser(userAdmin);
		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);
	}
}
