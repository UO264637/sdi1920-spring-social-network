package com.uniovi.entities;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User entity, it holds the information about the application users (email, name and surname)
 * It also holds the security information being this the password hash and user role
 * Users can request friendships or other users and also accept those requested friendships
 */
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

	@OneToMany(mappedBy="requester", cascade=CascadeType.ALL, orphanRemoval = true)
	private Set<Friendship> friendships;
	@OneToMany(mappedBy="requested", orphanRemoval = true)
	private Set<Friendship> friendshipsReceived;

	public User() {
		friendships = new HashSet<>();
		friendshipsReceived = new HashSet<>();
	}

	public User(String email, String name, String surname) {
		this();
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.password = "123"; // No me pegues, es para probar		//Tranquila yo iba hacer algo parecido
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

	public Set<Friendship> getFriendships() {
		return friendships;
	}

	public void setFriendships(Set<Friendship> friendships) {
		this.friendships = friendships;
	}

	public Set<Friendship> getFriendshipsReceived() {
		return friendshipsReceived;
	}

	public void setFriendshipsReceived(Set<Friendship> friendshipsReceived) {
		this.friendshipsReceived = friendshipsReceived;
	}

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

	/**
	 * Combines name and surname
	 * @return full name of the user
	 */
	public String getFullName() {
		return name + " " + surname;
	}

	/**
	 * Request the friendship of another user
	 * @param requested	User to add as friend
	 */
	public void requestFriendship(User requested) {
		Friendship friendship = new Friendship(this, requested);
		this.friendships.add(friendship);
		requested.getFriendshipsReceived().add(friendship);
	}

	/**
	 * Es probable que este método deba funcionar recibiendo el usuario a aceptar en vez de la amistad, esto es un placeholder
	 * 
	 * 
	 * Accept the friendship of another user
	 * @param frienship	Friensdhip to be accepted
	 */
	public void acceptFriendship(Friendship friendship) {
		friendship.setPending(false);
	}
	
	/**
	 * Devuelve true o false según si el usuario pedido es amigo o no
	 * @param user	Usuario a comprobar si es amigo
	 * @return		true si es amigo, false en caso contrario
	 */
	public boolean isFriend(User user) {
		return friendships.stream().filter(x -> !x.isPending()).map(x -> x.getRequested()).collect(Collectors.toList()).contains(user) ||
				friendshipsReceived.stream().filter(x -> !x.isPending()).map(x -> x.getRequester()).collect(Collectors.toList()).contains(user);
	}
	
	/**
	 * Devuelve true o false según si si se le ha pedido amistad ya a ese amigo o no
	 * @param user	Usuario a comprobar si se ha solicitado su amistad
	 * @return		true si se ha solicitado ya, false en caso contrario
	 */
	public boolean isRequested(User user) {
		return friendships.stream().filter(x -> x.isPending()).map(x -> x.getRequested()).collect(Collectors.toList()).contains(user);
	}
	
	
}
