package com.shivay.medref.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shivay.medref.models.Address;
import com.shivay.medref.models.Doctor;
import com.shivay.medref.models.UserDetail;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Long>{
		public Doctor getDoctorByDoctorEmail(String email);
}
