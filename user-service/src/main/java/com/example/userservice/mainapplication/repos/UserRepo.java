package com.example.userservice.mainapplication.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.userservice.mainapplication.entities.UserAccount;

@Repository
public interface UserRepo extends JpaRepository<UserAccount,Long> {

	public UserAccount findByUsername(String username);
	public UserAccount findByEmail(String email);
	
	
}
