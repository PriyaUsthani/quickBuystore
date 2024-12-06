package com.example.userservice.mainapplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.userservice.mainapplication.dtos.UserDTO;
import com.example.userservice.mainapplication.entities.UserAccount;
import com.example.userservice.mainapplication.repos.UserRepo;

@Service
public class UserServiceLayer {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepo userrepo;
	
	
	public UserAccount createAccount(UserDTO userdto) {
		UserAccount accountInfo = new UserAccount();
		accountInfo.setUsername(userdto.getUsername());
		accountInfo.setEmail(userdto.getEmail());
		accountInfo.setPassword(passwordEncoder.encode(userdto.getPassword()));
		return userrepo.save(accountInfo);
	}

}
