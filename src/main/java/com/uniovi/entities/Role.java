package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Role entity, it will contain both types of roles
 */
@Entity
public class Role {

	@Id @GeneratedValue
	private Long id;

	@OneToMany(mappedBy = "role")
	private Set<User> users;

	// Role identity string
	private String name;

	/**
	 * Empty constructor for bean purposes
	 */
	public Role() { }

	/**
	 * @param name Role name
	 */
	public Role(String name) {
		this();
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return new HashSet<>(users);
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "role='" + name + '\'';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Role role1 = (Role) o;
		return name.equals(role1.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
