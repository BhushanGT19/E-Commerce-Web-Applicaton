package com.bookcharm.app.controller;

import com.bookcharm.app.exception.AuthenticationFailedException;
import com.bookcharm.app.model.Address;
import com.bookcharm.app.service.AddressService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    

    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        // Add logic for creating a new address
        Address createdAddress = addressService.createAddress(address);
        return ResponseEntity.ok(createdAddress);
    }

    @PutMapping
    public ResponseEntity<Address> updateAddress(@RequestHeader String Authorization, @RequestBody Address address) {
        // Add logic for updating an existing address
       return null;
        
    }

   
@GetMapping
    
    public ResponseEntity<?> getUserAdderss(@RequestHeader String Authorization){
    	
    	
    	try {
    		Optional<Address> address = addressService.getAddressOfUser(Authorization);
    		return new ResponseEntity<Optional<Address>>(address,HttpStatus.OK);
    	}catch(AuthenticationFailedException ex){
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
    		
    	}
    	
    }

    // Other AddressController methods
}
