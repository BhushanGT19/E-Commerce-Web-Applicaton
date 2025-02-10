package com.bookcharm.app.controller;

import com.bookcharm.app.dto.AdminLoginDto;
import com.bookcharm.app.dto.AdminResponse;
import com.bookcharm.app.exception.AuthenticationFailedException;
import com.bookcharm.app.exception.UserNotFoundException;
import com.bookcharm.app.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody AdminLoginDto adminLoginDto){
    	
    	try {
			AdminResponse adminResponse = adminService.loginAdmin(adminLoginDto);
    		return new ResponseEntity<AdminResponse>(adminResponse,HttpStatus.OK);
    	}catch(UserNotFoundException ex) {
    		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT_FOUNT "+ ex.getMessage());
    	}catch(AuthenticationFailedException ex) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed "+ex.getMessage());
    	}catch (Exception e){
			return ResponseEntity.internalServerError().body("Error occurred while authenticating admin");
		}
    	
    }
    
}
