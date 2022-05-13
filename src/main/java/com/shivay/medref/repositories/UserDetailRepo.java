package com.shivay.medref.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shivay.medref.models.UserDetail;

@Repository
public interface UserDetailRepo extends JpaRepository<UserDetail, Long>{
	
	public UserDetail getUserByFirstName(String firstName);
	
	public UserDetail getUserByEmail(String email);
	
	public List<UserDetail> findUserByPhoneNumber(Long phoneNumber);
	
    public UserDetail findByVerifactionCode(String code);
}
