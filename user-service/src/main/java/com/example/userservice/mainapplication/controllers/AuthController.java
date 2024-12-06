package com.example.userservice.mainapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userservice.mainapplication.components.TokenProvider;
import com.example.userservice.mainapplication.dtos.AuthDTO;
import com.example.userservice.mainapplication.services.CustomUserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
    private CustomUserService service;
	
	@Autowired
	private TokenProvider provider;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthDTO dto) {
		String username = dto.getUsername();
		String password = dto.getPassword();
		String token = null;
		UserDetails user = service.loadUserByUsername(username);
		if(user!=null && encoder.matches(password,user.getPassword() )) {
		
		 token = provider.generateToken(user);
		 return ResponseEntity.ok(token);
		}
		else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("mismatched password");
		}
		
	}

}
