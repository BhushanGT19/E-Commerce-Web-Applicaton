package com.bookcharm.app.service;

import com.bookcharm.app.dto.*;
import com.bookcharm.app.exception.AuthenticationFailedException;
import com.bookcharm.app.exception.ClientErrorException;
import com.bookcharm.app.exception.UserNotFoundException;
import com.bookcharm.app.model.Admin;
import com.bookcharm.app.model.Seller;
import com.bookcharm.app.model.User;
import com.bookcharm.app.repository.AdminRepository;
import com.bookcharm.app.repository.SellerRepository;

import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;
    private SellerRepository sellerRepository;
    
    
    private WebClient.Builder builder;

    final private WebClient authenticationServiceWebClient;
    final private WebClient mailServiceWebClient;

    public AdminServiceImpl(@Value("${url.authentication-service-base-url}") String authenticationServiceBaseUrl, @Value("${url.notification-service-base-url}") String notificationServiceBaseUrl,  WebClient.Builder builder) {
        this.builder = builder;
        this.authenticationServiceWebClient = builder.baseUrl(authenticationServiceBaseUrl).build();
        this.mailServiceWebClient = builder.baseUrl(notificationServiceBaseUrl).build();
    }
    

    @Override
    public AdminResponse loginAdmin(AdminLoginDto adminLoginDto) {
    	
    	
    	String userName = adminLoginDto.getUserName();
    	String passWord = adminLoginDto.getPassWord();



        try{
            Optional<Admin> optionalAdmin = adminRepository.findByUserName(userName);

            if (optionalAdmin.isPresent()){

                Admin admin = optionalAdmin.get();
                AdminLoginValidationDto adminLoginValidationDto = new AdminLoginValidationDto();
                adminLoginValidationDto.setAdminId(admin.getAdminId());
                adminLoginValidationDto.setPassWord(admin.getPassWord());
                adminLoginValidationDto.setValidationPassWord(adminLoginDto.getPassWord());

                AdminResponse adminResponse = authenticationServiceWebClient.post().uri("/admin/login").body(BodyInserters.fromValue(adminLoginValidationDto)).retrieve().onStatus(HttpStatus::is4xxClientError,clientResponse ->  handleClientError(clientResponse)).bodyToMono(AdminResponse.class).block();
                // if everything is fine return response

                System.out.println(adminResponse.getToken());

                return new AdminResponse(admin, adminResponse.getToken());
            }else {
                throw new UserNotFoundException("Admin with username not found");
            }


        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }



    }
    
   
   
    
 // handle client error if, token is invalid throw UnauthorizedAccessException or throw ClientErrorException
    private Mono<? extends Throwable> handleClientError(ClientResponse clientResponse) {

        // through an error when user password not matched with passed password
        if(clientResponse.statusCode().equals(HttpStatus.UNAUTHORIZED)){
            return Mono.error(new AuthenticationFailedException("password not match"));
        }

        return Mono.error(new ClientErrorException("Client Error: " + clientResponse.statusCode()));

    }


	

}
