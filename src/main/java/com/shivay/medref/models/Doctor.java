package com.shivay.medref.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "doctor")
@Scope(value=WebApplicationContext.SCOPE_SESSION, proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Doctor {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOCTOR_SEQ")
	@SequenceGenerator(name = "APPOINTMENT_SEQ", allocationSize = 50, initialValue = 5)
	@Column(name = "doctor_id")
	private Long doctorId;
	@Column(name = "doctor_name")
	private String doctorName;
	@Column(name = "doctor_specialization")
	private String doctorSpecialization;
	private String doctorEmail;
	private String doctorCity;
	private String photo;

	
}
