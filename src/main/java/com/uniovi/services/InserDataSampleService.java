package com.uniovi.services;

import com.uniovi.entities.Friendship;
import com.uniovi.entities.Publication;
import com.uniovi.entities.Role;
import com.uniovi.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

/**
 * This service populates the database of basic data.
 * Testing data added should be omitted when it is finally deployed
 */
@Service
public class InserDataSampleService {

	@Autowired
	private RolesService rolesService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private PublicationsService publicationsService;

	@PostConstruct
	public void init() {
		
		// Roles creation
		Role admin = createRoleIfNotFound(RolesService.ROLES[0]);
		Role standard = createRoleIfNotFound(RolesService.ROLES[1]);
		
		// Admin user creation
		User userAdmin = new User("admin@email.com", "Admin", "Admin");
		userAdmin.setRole(admin);
		userAdmin.setPassword("admin");
		usersService.addUser(userAdmin);
		
		// Insertion of testing data, it should be commented or edited when deployed
		initTestData(standard, userAdmin);
	}

	private void initTestData(Role standard, User userAdmin) {
		
		// Users creation
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
		
		// Persisting users
		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);
		
		// Frienships creation
		user1.requestFriendship(user2);
		user1.acceptFriendship((Friendship) user2.getFriendshipsReceived().toArray()[0]);		// pero que feo coño, ya haré otro método mejor, creo
		user1.requestFriendship(user3);
		user1.requestFriendship(user4);
		user1.requestFriendship(user5);
		usersService.updateUser(user1);
		user2.requestFriendship(user3);
		usersService.updateUser(user2);
		user3.requestFriendship(user4);
		usersService.updateUser(user3);
		user4.requestFriendship(user5);
		usersService.updateUser(user4);
		
		// Publications creation
		Publication publication1 = new Publication("Title1", "Text1", user1);
		publicationsService.addPublication(publication1);
		Publication publication2 = new Publication("Title2", "Text2", user2);
		publicationsService.addPublication(publication2);
		Publication publication3 = new Publication("Title3", "Text3", user3);
		publicationsService.addPublication(publication3);
		Publication publication4 = new Publication("Title4", "Text4", user4);
		publicationsService.addPublication(publication4);
		Publication publication5 = new Publication("Title5", "Text5", user5);
		publicationsService.addPublication(publication5);
		Publication publication6 = new Publication("Title6", "Text6", user6);
		publicationsService.addPublication(publication6);
		Publication publication7 = new Publication("Title7", "Text7", user1);
		publicationsService.addPublication(publication7);
		Publication publication8 = new Publication("Title8", "Text8", user2);
		publicationsService.addPublication(publication8);
	}
	
	@Transactional
    private Role createRoleIfNotFound(String name) {
  
        Role role = rolesService.findByName(name);
        if (role == null) {
            role = new Role(name);
            rolesService.addRole(role);
        }
        return role;
    }
}
