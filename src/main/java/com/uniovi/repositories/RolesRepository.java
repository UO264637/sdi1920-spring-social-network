package com.uniovi.repositories;

import com.uniovi.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends CrudRepository<Role, Long> {

	Role findByName(String name);

}
