package com.example.userservice.mainapplication.components;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.userservice.mainapplication.services.CustomUserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
	@Autowired
	private TokenProvider provider;
	
	@Autowired
	private CustomUserService service;

	public JwtFilter(TokenProvider provider, CustomUserService service) {
		this.provider=provider;
		this.service=service;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String header = request.getHeader("Authorization");
		String token= null;
	    String username = null;
	    
	    if(header!=null && header.startsWith("Bearer ")) {
	    	token = header.substring(7);
	    	username = provider.extractUsername(token);
	    }
	    if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
		    UserDetails user = service.loadUserByUsername(username);
	           if(provider.validateToken(token,user)) {
		    	UsernamePasswordAuthenticationToken validatedToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
		    	validatedToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		    	SecurityContextHolder.getContext().setAuthentication(validatedToken);
		    }
	 }
	    filterChain.doFilter(request, response);
	}
}
