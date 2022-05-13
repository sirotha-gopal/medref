package com.shivay.medref.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shivay.medref.models.UserDetail;
import com.shivay.medref.repositories.UserDetailRepo;



public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserDetailRepo userRepo;
  
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
		UserDetail userByEmail = null;
		
			userByEmail = userRepo.getUserByEmail(email);
			
		
		
		if(userByEmail == null) {
			throw new UsernameNotFoundException("No User Fount with this Name::"+email);
		}
		
		return new MyUserDetails(userByEmail);
	}

}
