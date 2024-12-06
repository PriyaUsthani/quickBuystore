package com.example.userservice.mainapplication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.userservice.mainapplication.components.JwtFilter;
import com.example.userservice.mainapplication.components.TokenProvider;
import com.example.userservice.mainapplication.services.CustomUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private TokenProvider provider;
	
	@Autowired
	private CustomUserService service;
	
	@Bean
	 PasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	  JwtFilter jwtfilter() {
		return new JwtFilter(provider,service);
	}
	@Bean
	 SecurityFilterChain filterchain(HttpSecurity http) throws Exception{
		http.csrf(csrf->csrf.disable())
		.authorizeHttpRequests(auth->auth  
				.requestMatchers("/user/createAccount","/user/testAPI").permitAll()
				.requestMatchers("/auth/login").permitAll()
			  	.anyRequest().authenticated()
				)
		.sessionManagement(sessionManagement->sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		
		.addFilterBefore(jwtfilter(),UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	

}
