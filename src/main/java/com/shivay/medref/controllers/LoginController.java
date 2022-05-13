package com.shivay.medref.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.shivay.medref.models.Address;
import com.shivay.medref.models.UserDetail;

@Controller
//@RequestMapping()
public class LoginController {
	
	
	
	/* Login Page */
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView showLoginPage() {
		Map<String,Object> model = new HashMap<String, Object>();
		
		model.put("userDetail", new UserDetail());
		model.put("addressDetail", new Address());
		
		return new ModelAndView("login","model",model);
	}
	



}
