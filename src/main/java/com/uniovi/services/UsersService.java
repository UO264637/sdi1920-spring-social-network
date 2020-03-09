package com.uniovi.services;

import java.util.*;

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


	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
	}
	
	public void updateUser(User user) {
		usersRepository.save(user);
	}

	public List<User> getUsers() {
		List<User> users = new ArrayList<>();
		usersRepository.findAll().forEach(users::add);
		return users;
	}

	public Page<User> getOtherUsers(Pageable pageable, User user) {
		Page<User> users = usersRepository.findOtherUsers(pageable, user.getEmail(), rolesRepository.findByName(RolesService.ROLES[0]));
		return users;
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
	
	public Page<User> searchUserByNameSurnameAndEmail (Pageable pageable, String searchText, User user){
		searchText = "%"+searchText+"%";
		
		Page<User> users = usersRepository.searchByNameSurnameAndEmail(pageable, searchText, user.getEmail(), rolesRepository.findByName(RolesService.ROLES[0]));
		
		return users;
	}
}