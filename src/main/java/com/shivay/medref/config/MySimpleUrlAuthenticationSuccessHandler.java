package com.shivay.medref.config;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.shivay.medref.models.Doctor;
import com.shivay.medref.models.UserDetail;
import com.shivay.medref.repositories.DoctorRepo;
import com.shivay.medref.repositories.UserDetailRepo;

public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	
	@Autowired
	DoctorRepo docRepo;
	
	
	
    protected Log logger = LogFactory.getLog(this.getClass());

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    
    
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		logger.info("Inside onAuthenticationSuccess Handler Method::");
		
		
		handle(request, response, authentication);
	    clearAuthenticationAttributes(request);	
		
	}
	
	protected void handle(
	        HttpServletRequest request,
	        HttpServletResponse response, 
	        Authentication authentication
	) throws IOException {

	    String targetUrl = determineTargetUrl(authentication);

	    if (response.isCommitted()) {
	        logger.debug(
	                "Response has already been committed. Unable to redirect to "
	                        + targetUrl);
	        return;
	    }

	    redirectStrategy.sendRedirect(request, response, targetUrl);
	}
	
	
	protected String determineTargetUrl(final Authentication authentication) {

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("Authentication ::"+authentication);
		 Object credentials = authentication.getCredentials();
		System.out.println(credentials);
		logger.info(credentials);
		MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
		System.out.println(principal.getUsername());
		
		
		
			
		
		
	    Map<String, String> roleTargetUrlMap = new HashMap<>();
	    roleTargetUrlMap.put("ROLE_USER", "/");
	    roleTargetUrlMap.put("ROLE_DOCTOR", "/doctor/");
	    roleTargetUrlMap.put("ROLE_ADMIN", "/admin/dashboard");
	    

	    final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	    for (final GrantedAuthority grantedAuthority : authorities) {
	        String authorityName = grantedAuthority.getAuthority();
	        if(roleTargetUrlMap.containsKey(authorityName)) {
	        	if(authorityName.equals("ROLE_DOCTOR")) {
	        		return roleTargetUrlMap.get(authorityName)+principal.getUsername();
	        	}
	        		
	        	
	        	
	            return roleTargetUrlMap.get(authorityName);
	        }
	    }

	    throw new IllegalStateException();
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request) {
	    HttpSession session = request.getSession(false);
	    if (session == null) {
	        return;
	    }
	    session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
	
}
