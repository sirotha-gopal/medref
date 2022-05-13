package com.shivay.medref.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shivay.medref.models.Appointment;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long>{
	
	public List<Appointment> findByDoctorId(Long doctorId);
	public List<Appointment> findByPatientEmail(String patientEmail);
	
}
