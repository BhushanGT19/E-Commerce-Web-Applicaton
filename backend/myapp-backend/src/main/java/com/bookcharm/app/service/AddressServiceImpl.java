package com.bookcharm.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookcharm.app.exception.UnauthorizedAccessException;
import com.bookcharm.app.model.Address;
import com.bookcharm.app.repository.AddressRepository;
import com.bookcharm.app.utils.JwtUtil;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Address getAddressById(Long addressId) {
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        return optionalAddress.orElse(null);
    }

    @Override
    public Address createAddress(Address address) {
        // Add logic for address creation, validation, etc.
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Long addressId, Address updatedAddress) {
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isPresent()) {
            Address existingAddress = optionalAddress.get();
            // Update the existing address with the new information
            existingAddress.setCity(updatedAddress.getCity());
            existingAddress.setState(updatedAddress.getState());
            existingAddress.setCountry(updatedAddress.getCountry());
            existingAddress.setPostalCode(updatedAddress.getPostalCode());
            existingAddress.setLandmark(updatedAddress.getLandmark());

            // Save and return the updated address
            return addressRepository.save(existingAddress);
        }
        return null;
    }

    @Override
    public boolean deleteAddress(Long addressId) {
        if (addressRepository.existsById(addressId)) {
            addressRepository.deleteById(addressId);
            return true;
        }
        return false;
    }

	@Override
	public Optional<Address> getAddressOfUser(String authorization) {
		// TODO Auto-generated method stub
		
		Optional<Long> userId = jwtUtil.verifyUser(authorization);
		
		if(userId.isPresent()) {
			//userId and addressId will be same due to oneToone Relationship
			Optional<Address> address = addressRepository.findAddressByAddressId(userId.get());
			return address;
		}else {
			throw new UnauthorizedAccessException("User Not Found");
		}
	}

    // Add other AddressService methods if needed
}
