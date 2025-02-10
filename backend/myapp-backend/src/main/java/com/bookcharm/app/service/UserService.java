package com.bookcharm.app.service;

import java.util.List;
import java.util.Set;

import com.bookcharm.app.dto.LoginResponse;
import com.bookcharm.app.dto.RegistrationResponse;
import com.bookcharm.app.dto.UserLoginDto;
import com.bookcharm.app.dto.UserRegistrationDto;
import com.bookcharm.app.model.Address;
import com.bookcharm.app.model.Order;
import com.bookcharm.app.model.User;

public interface UserService {
    User getUserById(Long userId);
    RegistrationResponse createUser(UserRegistrationDto userRegistrationDto);
    LoginResponse loginUser(UserLoginDto userLoginDto);
    User updateUser(Long userId, User user);
    boolean deleteUser(Long userId);
    Set<Order> getAllOrdersOfUsers(String authorization);
	
	


    // Cart-related methods
//    void addToCart(Long userId, Long productId, Integer quantity);
//    void removeFromCart(Long userId, Long productId);

    // Other UserService methods
}
