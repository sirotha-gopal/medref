package com.shivay.medref.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.shivay.medref.models.Address;
import com.shivay.medref.models.Appointment;
import com.shivay.medref.models.Doctor;
import com.shivay.medref.models.UserDetail;
import com.shivay.medref.repositories.AddressRepo;
import com.shivay.medref.repositories.AppointmentRepo;
import com.shivay.medref.repositories.DoctorRepo;
import com.shivay.medref.repositories.UserDetailRepo;
import com.shivay.medref.services.UserService;
import com.shivay.medref.util.FileUploadUtil;

import net.bytebuddy.utility.RandomString;



@Controller
public class UserController {

	@Autowired
	public UserDetailRepo userRepo;

	@Autowired
	public AddressRepo addressRepo;

	@Autowired
	public DoctorRepo doctorRepo;

	@Autowired
	public AppointmentRepo appointmentRepo;

	@Autowired
	public BCryptPasswordEncoder passEncoder;
	
	@Autowired
	public JavaMailSender mailSender;
	
	@Autowired
	public UserService userService;
	
	@Autowired
	public UserDetailsService userDetailsService;
	
    @Autowired
    private AuthenticationManager authenticationManager;
	
	

	/* Home Page */

	@GetMapping({ "/", "/home" })
	public String showFirstPage(Authentication auth,Model model) {

		if(auth!=null) {
			if(auth.isAuthenticated()) {
			
				
			
			UserDetail userByEmail = userRepo.getUserByEmail(auth.getName());
			
			model.addAttribute("user", userByEmail);
			}
		}
	
		return "/user/index";
	}

	/* Store Page */

	@RequestMapping(value = "/shop", method = RequestMethod.GET)
	public String showStorePage() {

		return "shop";
	}

	/* Signup Page */

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView showSignupPage() {
		
		
		ModelAndView model = new ModelAndView();
		model.setViewName("/signup");
		model.addObject("user", new UserDetail());
		model.addObject("address", new Address());
		return model;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST) 
	public ModelAndView signUpNewUser(UserDetail user, Address address, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {

		System.out.println("==============================================================");
		System.out.println("==============================================================");

		ModelAndView model = new ModelAndView();
		
		
		String encodedPassword = passEncoder.encode(user.getPassword());

		System.out.println(user);
		System.out.println(address);

		/*
		 * We First need to save this object because we are going to use it's primary
		 * key as foreign key
		 */

		Address savedAddress = addressRepo.save(address);

		user.setAddress(savedAddress);
		user.setRole("ROLE_USER");
		user.setPassword(encodedPassword);
		user.setEnabled(false);
		user.setPhoto("");
		String randomCode = RandomString.make(64);
		user.setVerifactionCode(randomCode);
		model.setViewName("/user/index");
		UserDetail savedUser = null;
		try {
			savedUser = userRepo.save(user);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addObject("duplicateEmail", true);
			
			return model;
			
		}
		
		sendVerificationEmail(savedUser, getSiteUrl(request));
		model.addObject("mailSent", true);

		return model;
	}
	
	public String getSiteUrl(HttpServletRequest request) {
		 String siteURL = request.getRequestURL().toString();
	     System.out.println("SiteUrl::"+siteURL);
	     System.out.println("ServletPath::"+request.getServletPath());
		 return siteURL.replace(request.getServletPath(), "");
	}
	
	public void sendVerificationEmail(UserDetail user, String siteURL) throws UnsupportedEncodingException, MessagingException {
		
		String toAddress = user.getEmail();
	    String fromAddress = "lnctgrievance@gmail.com";
	    String senderName = "MEDREF";
	    String subject = "Please verify your registration";
	    String content = "Dear [[name]],<br>"
	            + "Please click the link below to verify your registration:<br>"
	            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
	            + "Thank you,<br>"
	            + "MEDREF";
	            
	        
	     
	    MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom(fromAddress, senderName);
	    helper.setTo(toAddress);
	    helper.setSubject(subject);
	     
	    content = content.replace("[[name]]", user.getFirstName()+" "+user.getLastName());
	    String verifyURL = siteURL + "/verify?code=" + user.getVerifactionCode();
	     
	    content = content.replace("[[URL]]", verifyURL);
	     
	    helper.setText(content, true);
	     
	    mailSender.send(message);
		
		
	}
	
	
	@GetMapping("/verify")
	public String verify(@Param("code")String code,Model model) throws Exception {
		
		if (userService.verify(code)) {
			model.addAttribute("isVerified",true);
	        return "/login";
	    } else {
	        throw new Exception();
	    }
	}

	/* About Page */
	@GetMapping("/about")
	public ModelAndView showAboutPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("about");

		return model;
	}

	/* Contact Page Page */
	@GetMapping("/contact")
	public ModelAndView showContactPage(Principal principal) {
		UserDetail userDetail = userRepo.getUserByEmail(principal.getName());
		System.out.println("User Detail1 ::"+ userDetail);
		ModelAndView model = new ModelAndView();
		model.setViewName("/user/contact");
		model.addObject("user", userDetail);

		return model;
	}

	/* Cart Page */
	@GetMapping("/cart")
	public ModelAndView showCartPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("cart");

		return model;
	}

	/* Check out Page */
	@GetMapping("/checkout")
	public ModelAndView showCheckoutPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("checkout");

		return model;
	}

	@GetMapping("/demo")
	public String showDemoPage() {
		return "/sidebars/index";
	}

	@GetMapping("/user-profile")
	public String showUserProfilePage(Model model,Principal principal) {

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		
		
		UserDetail userDetail = userRepo.getUserByEmail(principal.getName());
		String role = userDetail.getRole();
		if(role.equalsIgnoreCase("ROLE_DOCTOR")) {
			System.out.println("Inside Doctor.......");
			Doctor doctorByDoctorEmail = doctorRepo.getDoctorByDoctorEmail(principal.getName());
			model.addAttribute("doctor", doctorByDoctorEmail);
		}
		
		System.out.println("User Detail1 ::"+ userDetail);
		
		model.addAttribute("user", userDetail);
		model.addAttribute("address", userDetail.getAddress());

		return "/user/user-profile";
	}

	@GetMapping("/my-appointments")
	public String showMyAppointmentsPage(Model model,Principal principal) {

		UserDetail userDetail = userRepo.getUserByEmail(principal.getName());
		
		List<Appointment> findByPatientEmail = appointmentRepo.findByPatientEmail(userDetail.getEmail());
		model.addAttribute("appointments", findByPatientEmail);
		model.addAttribute("user", userDetail);
		return "/user/my_appointments";

	}

	@GetMapping("/closed/{id}")
	public String previousAppointments(@PathVariable String id, Model model,Principal principal) {
		UserDetail userDetail = userRepo.getUserByEmail(principal.getName());
		
		
		List<Appointment> findByPatientEmail = appointmentRepo.findByPatientEmail(id);
		model.addAttribute("user", userDetail);
		model.addAttribute("appointments", findByPatientEmail);
		model.addAttribute("isTrueOrFalse", false);
		return "/user/previous_appointments";

	}

	@PostMapping("/upload-image")
	public String uploadImage( @RequestParam("image") MultipartFile multipartFile,Principal principal) throws IOException {
		System.out.println(multipartFile.getSize());
		
		UserDetail userDetail = userRepo.getUserByEmail(principal.getName());
		
		
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		userDetail.setPhoto("/user-photos/" + userDetail.getFirstName()+userDetail.getUserId()+"/"+fileName);
		if(userDetail.getRole().equalsIgnoreCase("ROLE_DOCTOR")) {
			System.out.println("Inside Doctor");
			System.out.println("Inside Doctor");
			System.out.println("Inside Doctor");
			System.out.println("Inside Doctor");
			System.out.println("Inside Doctor");
			System.out.println("Inside Doctor");
			System.out.println("Inside Doctor");
			System.out.println("Inside Doctor");
			System.out.println("Inside Doctor");
			System.out.println("Inside Doctor");
			System.out.println("Inside Doctor");
			System.out.println("Inside Doctor");
			System.out.println("Inside Doctor");
			Doctor doctorByDoctorEmail = doctorRepo.getDoctorByDoctorEmail(principal.getName());
			System.out.println("BEFOR SAVE::"+doctorByDoctorEmail);
			doctorByDoctorEmail.setPhoto("/user-photos/" + userDetail.getFirstName()+userDetail.getUserId()+"/"+fileName);
			doctorRepo.save(doctorByDoctorEmail);
			System.out.println("AFTER SAVE::"+doctorByDoctorEmail);
		}
		
		UserDetail save = userRepo.save(userDetail);

		String uploadDir = "user-photos/" + save.getFirstName()+save.getUserId();

		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

		return "redirect:/user-profile";
	}
	
	@PostMapping("/update-profile")
	public String updateProfile(UserDetail user,@RequestParam String city,@RequestParam String state,Principal principal,HttpServletRequest request) {
		UserDetail userDetail = userRepo.getUserByEmail(principal.getName());
		String role = userDetail.getRole();
		
		userDetail.setFirstName(user.getFirstName());
		userDetail.setLastName(user.getLastName());
		userDetail.setAge(user.getAge());
		userDetail.setPhoneNumber(user.getPhoneNumber());
		Address address = userDetail.getAddress();
		address.setCity(city);
		address.setState(state);
		addressRepo.save(address);
		userDetail.setAddress(address);
		userRepo.save(userDetail);
		if(role.equalsIgnoreCase("ROLE_DOCTOR")) {
			Doctor doctorByDoctorEmail = doctorRepo.getDoctorByDoctorEmail(principal.getName());
			doctorByDoctorEmail.setDoctorCity(city);
			String doctorSpecialization = request.getParameter("doctorSpecialization");
			doctorByDoctorEmail.setDoctorSpecialization(doctorSpecialization);
			doctorRepo.save(doctorByDoctorEmail);
			
		}
			
		return "redirect:/user-profile";
	}

	@GetMapping("/logout")
	public String logoutPerform(HttpServletRequest request) {
		System.out.println("Inside logout");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		
		HttpSession session = request.getSession();
		Enumeration<String> attributeNames = session.getAttributeNames();
		System.out.println(attributeNames);
		
		
		return "login";
	}
	
}
