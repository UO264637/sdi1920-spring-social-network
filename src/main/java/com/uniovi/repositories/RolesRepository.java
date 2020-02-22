package com.uniovi.repositories;

import com.uniovi.entities.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository {

	Role findByName(String name);

}
