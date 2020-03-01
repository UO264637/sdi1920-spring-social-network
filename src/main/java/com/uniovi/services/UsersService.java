package com.uniovi.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Role;
import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UsersRepository usersRepository;

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
	}

	public List<User> getUsers() {
		List<User> users = new ArrayList<>();
		usersRepository.findAll().forEach(users::add);
		return users;
	}

	public List<User> getOtherUsers(User user) {
		return usersRepository.findOtherUsers(user, new Role(RolesService.ROLES[1]));
	}

	public User getUser(Long id) {
		Optional<User> user = usersRepository.findById(id);
		return (user.isPresent()) ? usersRepository.findById(id).get() : null;
	}

	public User getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}

	public void deleteUser(Long id) {
		usersRepository.deleteById(id);
	}
}