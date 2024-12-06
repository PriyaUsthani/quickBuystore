package com.example.userservice.mainapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userservice.mainapplication.dtos.UserDTO;
import com.example.userservice.mainapplication.entities.UserAccount;
import com.example.userservice.mainapplication.services.UserServiceLayer;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserServiceLayer userservice;
	
	@PostMapping("/createAccount")
	public UserAccount create(@Valid @RequestBody UserDTO dto) {
		return userservice.createAccount(dto);
	}
	
	@GetMapping("/testAPI")
	public String get() {
		return "User Service API 's working perfectly";
	}

}
