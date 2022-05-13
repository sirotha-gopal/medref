package com.shivay.medref.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shivay.medref.models.Address;
import com.shivay.medref.models.Doctor;
import com.shivay.medref.models.UserDetail;
import com.shivay.medref.repositories.AddressRepo;
import com.shivay.medref.repositories.AppointmentRepo;
import com.shivay.medref.repositories.DoctorRepo;
import com.shivay.medref.repositories.UserDetailRepo;
import com.shivay.medref.util.FileUploadUtil;
import com.shivay.medref.util.Utility;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	public UserDetailRepo userRepo;
	
	@Autowired
	public AddressRepo addressReop;
	
	@Autowired
	public AddressRepo addressRepo;
	
	@Autowired
	public DoctorRepo doctorRepo;
	
	@Autowired
	public AppointmentRepo appointmentRepo;
	
	@Autowired
	public BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	/* Admin Dashboard */ 
	@GetMapping("/dashboard")
	public String showDashboard(Model model) {
		System.out.println("Inside showDashboard Method");
		Long countUser = 0L,countDoctor=0L;
		List<UserDetail> findAll = userRepo.findAll();
		System.out.println(findAll);
		for (UserDetail userDetail : findAll) {
			if(userDetail.getRole()!=null) {
			if(userDetail.getRole().equalsIgnoreCase("ROLE_USER"))
				countUser++;
			if(userDetail.getRole().equalsIgnoreCase("ROLE_DOCTOR"))
				countDoctor++;
			}
		}
		
		model.addAttribute("user", countUser);
		model.addAttribute("doctor", countDoctor);
		
		return "/admin/dashboard";
	}
		
	/* Admin Add Doctor */ 
	@GetMapping("/add-doctor")
	public String showAddDoctorPage(Model model) {
		
		model.addAttribute("doctor",new Doctor());
		model.addAttribute("user", new UserDetail());
		
		return "/admin/add-doctor";
	}
	

	@PostMapping("/add-doctor")
	public String addDoctorPage(Model model,Doctor doctor, UserDetail user,@RequestParam("image") MultipartFile multipartFile) throws IOException {
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
		String genPass = Utility.generateRandomPassword();
		System.out.println(user);
		System.out.println(genPass);
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		System.out.println(fileName);
		doctor.setPhoto(fileName);
		doctor.setDoctorName(user.getFirstName()+" "+user.getLastName());
		doctor.setDoctorEmail(user.getEmail());
		Doctor savedDoctor= doctorRepo.save(doctor);
		
		user.setPhoto(fileName);
		user.setPassword(bCryptPasswordEncoder.encode("123"));
		user.setRole("ROLE_DOCTOR");
		user.setEnabled(true);
		Address address = new Address();
		addressReop.save(address);
		user.setAddress(address);
		UserDetail save = userRepo.save(user);
		
		String uploadDir = "user-photos/" + save.getEmail();
		 
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		
		return "redirect:/admin/add-doctor?id="+genPass;
	}
	
	

	








}
