package com.shivay.medref.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.shivay.medref.models.UserDetail;
import com.shivay.medref.repositories.UserDetailRepo;

@Service
public class UserService{
	
	@Autowired
	UserDetailRepo userRepo;
	@Autowired
	UserDetailsService userDetailsServcie;
	@Autowired
	AuthenticationManager authenticationManager;
	
	public boolean verify(String verificationCode) {
	    UserDetail user = userRepo.findByVerifactionCode(verificationCode);
	     
	    if (user == null || user.isEnabled()) {
	        return false;
	    } else {
//	    	UserDetail findByVerifactionCode = userRepo.findByVerifactionCode(verificationCode);
//	    	autoLogin(findByVerifactionCode.getEmail(), findByVerifactionCode.getPassword());
	        user.setVerifactionCode(null);
	        user.setEnabled(true);
	        userRepo.save(user);
	         
	        return true;
	    }
	     
	}
	public void autoLogin(String username, String password) {
        UserDetails userDetails = userDetailsServcie.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }
	
	
	
}