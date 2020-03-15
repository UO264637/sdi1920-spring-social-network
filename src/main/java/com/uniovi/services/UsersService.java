package com.uniovi.services;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.RolesRepository;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private RolesRepository rolesRepository;

	private static final Logger logger = LoggerFactory.getLogger(UsersService.class);


	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
		logger.info("Created user with email " + user.getEmail());
	}
	
	public void updateUser(User user) {
		usersRepository.save(user);
		logger.info("Updated user with the email " + user.getEmail());
	}

	public List<User> getUsers() {
		List<User> users = new ArrayList<>();
		usersRepository.findAll().forEach(users::add);
		logger.info("Retrieved the full list of users");
		return users;
	}

	public Page<User> getOtherUsers(Pageable pageable, User user) {
		Page<User> users = usersRepository.findOtherUsers(pageable, user.getEmail(), rolesRepository.findByName(RolesService.ROLES[0]));
		return users;
	}

	public User getUser(Long id) {
		Optional<User> user = usersRepository.findById(id);
		logger.info("Retrieved user with id " + id);
		return (user.isPresent()) ? user.get() : null;
	}

	public User getUserByEmail(String email) {
		logger.info("The user with the email " + email);
		return usersRepository.findByEmail(email);
	}

	public void deleteUser(Long id) {
		logger.info("Deleted user with the id " + id);
		usersRepository.deleteById(id);
	}
	
	public Page<User> searchUserByNameSurnameAndEmail (Pageable pageable, String searchText, User user){
		searchText = "%"+searchText+"%";
		
		Page<User> users = usersRepository.searchByNameSurnameAndEmail(pageable, searchText, user.getEmail(), rolesRepository.findByName(RolesService.ROLES[0]));
		
		return users;
	}
}