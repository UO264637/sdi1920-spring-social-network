package com.uniovi.entities;

import javax.persistence.*;
import java.net.UnknownServiceException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;
	private String name;
	private String surname;

	private String password;
	@Transient
	private String confirmPassword;

	@ManyToOne
	private Role role;

//	@OneToMany
//	private Set<Friendship> friendships;
//	@OneToMany
//	private Set<Friendship> friendshipsReceived;

	public User() {
		//friendships = new HashSet<>();
		//friendshipsReceived = new HashSet<>();
	}

	public User(String email, String name, String surname) {
		this();
		this.email = email;
		this.name = name;
		this.surname = surname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

//	public Set<Friendship> getFriendships() {
//		return friendships;
//	}
//
//	public void setFriendships(Set<Friendship> friendships) {
//		this.friendships = friendships;
//	}
//
//	public Set<Friendship> getFriendshipsReceived() {
//		return friendshipsReceived;
//	}
//
//	public void setFriendshipsReceived(Set<Friendship> friendshipsReceived) {
//		this.friendshipsReceived = friendshipsReceived;
//	}

	@Override
	public String toString() {
		return "User{" +
				"email='" + email + '\'' +
				", name='" + name + '\'' +
				", surname='" + surname + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(id, user.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	// ---------------------------------------------------------------------

	public String getFullName() {
		return name + " " + surname;
	}

	public void requestFriendship(User requested) {
		//TODO
	}

	public void acceptFriendship(User requester) {
		//TODO
	}
}
