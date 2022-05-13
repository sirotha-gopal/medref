package com.shivay.medref.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shivay.medref.models.Appointment;
import com.shivay.medref.models.Doctor;
import com.shivay.medref.models.UserDetail;
import com.shivay.medref.repositories.AddressRepo;
import com.shivay.medref.repositories.AppointmentRepo;
import com.shivay.medref.repositories.DoctorRepo;
import com.shivay.medref.repositories.UserDetailRepo;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
	
	@Autowired
	public UserDetailRepo userRepo;
	
	@Autowired
	public AddressRepo addressRepo;
	
	@Autowired
	public DoctorRepo doctorRepo;
	
	@Autowired
	public AppointmentRepo appointmentRepo;
	
	@Autowired
	public UserDetail userDetail;

	
	@GetMapping("/{doctorEmail}")
	public String showDocotorsDashBoard(@PathVariable String doctorEmail, Model model,Authentication auth) {
		
		UserDetail userByEmail = userRepo.getUserByEmail(auth.getName());
		this.userDetail = userByEmail;
		
		
		Doctor doctorByDoctorEmail = doctorRepo.getDoctorByDoctorEmail(doctorEmail);
		System.out.println(doctorByDoctorEmail);
		
		List<Appointment> appointmentByDoctorId = appointmentRepo.findByDoctorId(doctorByDoctorEmail.getDoctorId());
		System.out.println(appointmentByDoctorId);
		model.addAttribute("appointments", appointmentByDoctorId);
		model.addAttribute("doctorId", doctorByDoctorEmail.getDoctorId());
		model.addAttribute("user", userDetail);
		model.addAttribute("isTrueOrFalse", true);		
		
		
		return "doctor/doctor_dashboard";
	}
	
	@GetMapping("/close/{appointmentId}")
	public String closeAppointment(@PathVariable String appointmentId,Principal principal) {
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
		
		System.out.println(appointmentId);
		
		Long appointmentID = Long.valueOf(appointmentId);
		
		Optional<Appointment> findById = appointmentRepo.findById(appointmentID);
		
			Appointment appointment = findById.get();
			appointment.setActive(false);
			appointmentRepo.save(appointment);
			
		
		
		return "redirect:/doctor/"+principal.getName();
	}
	@GetMapping("/closed/{doctorID}")
	public String closedAppointment(@PathVariable String doctorID, Model model) {
		
		Long doctorId = Long.valueOf(doctorID);
		
		List<Appointment> appointmentByDoctorId = appointmentRepo.findByDoctorId(doctorId);
		System.out.println(appointmentByDoctorId);
		model.addAttribute("appointments", appointmentByDoctorId);
		model.addAttribute("doctorId", doctorID);
		model.addAttribute("user", userDetail);
		model.addAttribute("isTrueOrFalse", false);		
		return "/user/previous_appointments";
	}
	
	
	
	
	








}
