package com.example.userservice.mainapplication.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.userservice.mainapplication.entities.UserAccount;
import com.example.userservice.mainapplication.repos.UserRepo;

@Service
public class CustomUserService implements UserDetailsService{
	
	@Autowired
	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	     UserAccount user = repo.findByUsername(username);
	     if(user==null) {
	    	 throw new UsernameNotFoundException("user with this username not found");
	     }
		return  new User(user.getUsername(),user.getPassword(), new ArrayList<>());
	}

}
