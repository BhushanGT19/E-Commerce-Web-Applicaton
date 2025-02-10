package com.bookcharm.app.repository;

import com.bookcharm.app.model.Address;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

	Optional<Address> findAddressByAddressId(Long long1);
    // Additional address-specific query methods if needed

	
}
