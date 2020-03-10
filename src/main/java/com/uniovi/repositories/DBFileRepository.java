package com.uniovi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uniovi.entities.DBFile;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {

}
