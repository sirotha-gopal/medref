package com.shivay.medref.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user_detail")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
@Table(name = "UserDetail")
public class UserDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "USER_DETAIL_SEQ")
	@SequenceGenerator(name = "USER_DETAIL_SEQ",allocationSize = 50, initialValue = 1)
	@Column(name = "user_id")
	private Long userId;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "age")
	private Integer age;
	@Column(name = "email",unique = true)
	private String email;
	@Column(name = "phone_number")
	private Long phoneNumber;
	@Column(name = "password")
	private String password;
	@Column(name = "role")
	private String role;
	@OneToOne
	@JoinColumn(name = "address")
	private Address address;
	private String photo;
	@Column(length = 64)
	private String verifactionCode;
	@Column
	private boolean enabled;
	
	@Transient
	private String repeatPassword;

	
	
		
	
	
	
	
	
	
	
	
	
}
