package com.shivay.medref.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "appointment")
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "APPOINTMENT_SEQ")
	@SequenceGenerator(name = "APPOINTMENT_SEQ",allocationSize = 50, initialValue = 1)
	private Long appointmentId;
	private Long doctorId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date appointmentDate;
	private String phoneNumber;
	private String patientFirstName;
	private String patientLastName;
	private  int patientAge;
	private String patientEmail;
	private boolean isActive;
}
