package com.auth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.models.User;

/*
 * @Author : lkb
 * This repository connects the database with the java application 
 * As we are using Spring data it translates the name of the method into the needed query
 * 
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	
	Optional<User> findUserByUsername(String username);

}
