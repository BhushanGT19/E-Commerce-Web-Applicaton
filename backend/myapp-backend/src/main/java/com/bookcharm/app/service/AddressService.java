package com.bookcharm.app.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.bookcharm.app.model.Address;

public interface AddressService {
    Address getAddressById(Long addressId);
    Address createAddress(Address address);
    Address updateAddress(Long addressId, Address address);
    boolean deleteAddress(Long addressId);
    // Other AddressService methods
	Optional<Address> getAddressOfUser(String authorization);
}
