package com.shivay.medref.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.shivay.medref.models.Address;
import com.shivay.medref.models.Appointment;
import com.shivay.medref.models.Doctor;
import com.shivay.medref.models.UserDetail;
import com.shivay.medref.repositories.AddressRepo;
import com.shivay.medref.repositories.AppointmentRepo;
import com.shivay.medref.repositories.DoctorRepo;
import com.shivay.medref.repositories.UserDetailRepo;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

	@Autowired
	public UserDetailRepo userRepo;

	@Autowired
	public AddressRepo addressRepo;

	@Autowired
	public DoctorRepo doctorRepo;

	@Autowired
	public AppointmentRepo appointmentRepo;

	/* Appointment Page */
	@GetMapping("/get-appointment")
	public ModelAndView showAppointmentPage(Principal principal) {

		ModelAndView model = new ModelAndView();
		model.setViewName("/user/book-appointment");

		List<Doctor> doctorList = doctorRepo.findAll();
		UserDetail userByEmail = userRepo.getUserByEmail(principal.getName());

		model.addObject("doctorList", doctorList);
		model.addObject("appointment", new Appointment());
		model.addObject("user", userByEmail);
		return model;
	}

	@PostMapping("/get-appointment")
	public ModelAndView bookAppointmentPage(Appointment appointment, ModelAndView model, Principal principal,
			Authentication auth) {
		System.out.println("================================Book Appointment===============================");
		System.out.println("===============================================================================");
		System.out.println("===============================================================================");
		System.out.println("===============================================================================");
		System.out.println("===============================================================================");
		System.out.println("===============================================================================");
		System.out.println("===============================================================================");
		System.out.println("===============================================================================");
		System.out.println("===============================================================================");
		System.out.println("===============================================================================");

		UserDetail userByEmail = userRepo.getUserByEmail(principal.getName());
		Class<? extends Object> class1 = auth.getPrincipal().getClass();
		System.out.println(class1);
		System.out.println(appointment);

		model.addObject("isNull", true);
		model.addObject("appointment", appointment);
		model.addObject("date", appointment.getAppointmentDate());
		model.addObject("user", userByEmail);
		List<Doctor> doctorList = doctorRepo.findAll();
		model.addObject("doctorList", doctorList);
		
		model.setViewName("/user/book-appointment");

		return model;
	}

	@PostMapping("/updateDetails")
	public ModelAndView updateAppointmentDetails(Appointment appointment, UserDetail userDetail) {

		System.out.println("===Update Details====");
		System.out.println("=====================");
		System.out.println("=====================");
		System.out.println("=====================");
		System.out.println("=====================");
		System.out.println("=====================");
		System.out.println("=====================");
		System.out.println("=====================");
		System.out.println("=====================");
		System.out.println("=====================");
		System.out.println("=====================");
		System.out.println("=====================");
		System.out.println("=====================");
		System.out.println("=====================");
		System.out.println("=====================");
		System.out.println("=====================");
		System.out.println("=====================");
		System.out.println("=====================");
		System.out.println("=====================");
		System.out.println("=====================");
		System.out.println("=====================");
		System.out.println("=====================");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println(userDetail);
		appointment.setPatientEmail(userDetail.getEmail());
		System.out.println(appointment);
		appointment.setActive(true);
		Appointment savedAppointment = appointmentRepo.save(appointment);
		System.out.println(savedAppointment);
		return new ModelAndView("redirect:/appointment/get-appointment?id=" + savedAppointment.getAppointmentId() + "");

	}

}
