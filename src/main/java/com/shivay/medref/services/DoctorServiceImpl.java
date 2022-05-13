package com.shivay.medref.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shivay.medref.models.UserDetail;
import com.shivay.medref.repositories.AddressRepo;
import com.shivay.medref.repositories.AppointmentRepo;
import com.shivay.medref.repositories.DoctorRepo;
import com.shivay.medref.repositories.UserDetailRepo;

@Service
public class DoctorServiceImpl implements DoctorService {

	
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
	
	
	
}
