package com.bookcharm.app.service;

import java.util.List;

import com.bookcharm.app.dto.AdminLoginDto;
import com.bookcharm.app.dto.AdminResponse;
import org.springframework.http.ResponseEntity;

import com.bookcharm.app.dto.UserLoginDto;
import com.bookcharm.app.model.Admin;
import com.bookcharm.app.model.Seller;

public interface AdminService {

	AdminResponse loginAdmin(AdminLoginDto adminLoginDto);

}
