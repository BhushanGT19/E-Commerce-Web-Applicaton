package com.bookcharm.app.controller;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookcharm.app.dto.LoginResponse;
import com.bookcharm.app.dto.RegistrationResponse;
import com.bookcharm.app.dto.UpdateUserDto;
import com.bookcharm.app.dto.UserLoginDto;
import com.bookcharm.app.dto.UserRegistrationDto;
import com.bookcharm.app.exception.AuthenticationFailedException;
import com.bookcharm.app.exception.EmailAlreadyExistsException;
import com.bookcharm.app.exception.InvalidEmailException;
import com.bookcharm.app.exception.UserNotFoundException;
import com.bookcharm.app.model.Address;
import com.bookcharm.app.model.Order;
import com.bookcharm.app.repository.UserRepository;
import com.bookcharm.app.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDto userLoginDto){

        try{
            LoginResponse loginResponse =  userService.loginUser(userLoginDto);
            return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
            
        }catch (UserNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found " + ex.getMessage());
        }catch (AuthenticationFailedException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication Failed " + ex.getMessage());
        }catch (InvalidEmailException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Authentication Failed : " + ex.getMessage());
        }

    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRegistrationDto userRegistrationDto) {

        // send a request to authentication-mail service microservice

        try {
            RegistrationResponse registrationResponse = userService.createUser(userRegistrationDto);
            return new ResponseEntity<RegistrationResponse>(registrationResponse, HttpStatus.CREATED);
        }
        catch (EmailAlreadyExistsException e) {

            return new ResponseEntity<>("Email address is already in use", HttpStatus.CONFLICT);

        }



    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestHeader String Authorization, @RequestBody UpdateUserDto updateUserDto) {

        String token = Authorization;
        System.out.println(token);

////        User updatedUser = userService.updateUser(userId, user);
//        if (updatedUser != null) {
//            return ResponseEntity.ok(updatedUser);
//        } else {
//            return ResponseEntity.notFound().build();
//        }

        return ResponseEntity.ok().body("Reached");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        boolean deleted = userService.deleteUser(userId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
    //get all the orders of users
    
    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrdersOfUsers(@RequestHeader String Authorization){
    	
    	
    	try {
    		Set<Order> orders = userService.getAllOrdersOfUsers(Authorization);
    		return new ResponseEntity<Set<Order> >(orders,HttpStatus.OK);
    	}catch(AuthenticationFailedException ex) {
    		return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED" + ex.getMessage());
    		
    	}
		
    	
    }
    



}
