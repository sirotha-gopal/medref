package com.shivay.medref.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shivay.medref.models.Address;
import com.shivay.medref.models.UserDetail;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long>{

}
