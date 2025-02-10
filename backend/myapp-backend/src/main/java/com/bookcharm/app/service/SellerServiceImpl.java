package com.bookcharm.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bookcharm.app.dto.*;
import com.bookcharm.app.exception.*;
import com.bookcharm.app.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookcharm.app.model.Seller;
import com.bookcharm.app.repository.SellerRepository;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SellerServiceImpl implements SellerService {


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SellerRepository sellerRepository;

    private final WebClient.Builder builder;
    private WebClient authenticationServiceWebClient;
    private WebClient mailServiceWebClient;

    public SellerServiceImpl(@Value("${url.authentication-service-base-url}") String authenticationServiceBaseUrl, @Value("${url.notification-service-base-url}") String notificationServiceBaseUrl, WebClient.Builder builder) {
        this.builder = builder;
        this.authenticationServiceWebClient = builder.baseUrl(authenticationServiceBaseUrl).build();
        this.mailServiceWebClient = builder.baseUrl(notificationServiceBaseUrl).build();
    }

    @Override
    public Seller getSellerById(Long sellerId) {
        Optional<Seller> optionalSeller = sellerRepository.findById(sellerId);
        return optionalSeller.orElse(null);
    }

    @Override
    public Seller createSeller(SellerRegistrationDto sellerRegistrationDto) {
        // Add logic for seller creation, validation, etc.

        // find whether seller already exists in db with email address

        // validate mobile number

        Optional<Seller> optionalSeller = sellerRepository.findByEmail(sellerRegistrationDto.getEmail());

        if (optionalSeller.isPresent()) {
            // throw error as user with email already exists
            Seller user = optionalSeller.get();
            throw new EmailAlreadyExistsException("Email address is already in use");
        } else {
            // means seller with email address does not exists
            // next steps
            // validate the seller details
            // email,
            // call authentication-mail service to create as well as get the User, and token if valid

            SellerRegistrationResponse sellerRegistrationResponse = authenticationServiceWebClient.post().uri("/seller/register")
                    .body(BodyInserters.fromValue(sellerRegistrationDto))
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, clientResponse -> handleClientError(clientResponse))
                    .bodyToMono(SellerRegistrationResponse.class)
                    .block();

            System.out.println(sellerRegistrationResponse.getSellerRegistrationDto());

            SellerRegistrationDto newSellerRegistrationDto = sellerRegistrationResponse.getSellerRegistrationDto();
            Seller newSeller = new Seller();
            newSeller.setEmail(newSellerRegistrationDto.getEmail());
            newSeller.setFirstName(newSellerRegistrationDto.getFirstName());
            newSeller.setLastName(newSellerRegistrationDto.getLastName());
            newSeller.setMobileNumber(newSellerRegistrationDto.getMobileNumber());
            newSeller.setPanNumber(newSellerRegistrationDto.getPanNumber());
            newSeller.setPassWord(newSellerRegistrationDto.getPassWord());


            newSeller = sellerRepository.save(newSeller);

            // notify to the user for,
            // greet and say we're reached to you by email once we verified your account

            try{
                mailServiceWebClient.post().uri("/seller/registration").body(BodyInserters.fromValue(newSeller)).retrieve().onStatus(HttpStatus::is4xxClientError,response -> handleClientError(response)).bodyToMono(Void.class).subscribe();
            }catch (Exception e){
                e.printStackTrace();
            }

            return newSeller;
        }
    }


    @Override
    public SellerResponse loginSeller(SellerLoginDto sellerLoginDto){

        // find whether user with email exists or not

        String email = sellerLoginDto.getEmail();
        String passWord = sellerLoginDto.getPassWord();

        Optional<Seller> optionalSeller = sellerRepository.findByEmail(email);

        if(optionalSeller.isPresent() && optionalSeller.get().isVerified()){

            Seller seller = optionalSeller.get();
            SellerLoginValidationDto sellerLoginValidationDto = new SellerLoginValidationDto();
            sellerLoginValidationDto.setSellerId(seller.getSellerId());
            sellerLoginValidationDto.setSellerPassWord(seller.getPassWord());
            sellerLoginValidationDto.setValidationPassWord(sellerLoginDto.getPassWord());

            String jwtToken = authenticationServiceWebClient.post().uri("/seller/login").body(BodyInserters.fromValue(sellerLoginValidationDto)).retrieve().onStatus(HttpStatus::is4xxClientError,clientResponse ->  handleClientError(clientResponse)).bodyToMono(SellerResponse.class).map(SellerResponse::getToken).block();
            // if everything is fine return response
            return new SellerResponse(seller, jwtToken);

        }else{
            // if user not exists
            throw new UserNotFoundException("Seller with " + email + " not found");
        }

    }

    @Override
    public Seller verifySeller(Long sellerId, String jwtToken) {


        Optional<Long> optionalAdminId  = jwtUtil.verifyAdmin(jwtToken);

        if(optionalAdminId.isPresent()){

            // find seller with id to modify it's state
            Optional<Seller> optionalSeller = sellerRepository.findById(sellerId);

            if (optionalSeller.isPresent()) {

                Seller existingSeller = optionalSeller.get();
                // mark the existing seller as verified seller
               existingSeller.setVerified(true);

                // notify the seller on email to notify them that they are registered successfully on a system
                // and congratulate them that they can now login on application as seller

                // Save and return the updated seller
                return sellerRepository.save(existingSeller);
            }else {

                throw new UserNotFoundException("Seller with this id not found");
            }

        }else{
            throw new UnauthorizedAccessException("unauthorized access to resource");
        }


    }

    @Override
    public boolean deleteSeller(Long sellerId, String jwtToken) {

        Optional<Long> optionalAdminId  = jwtUtil.verifyAdmin(jwtToken);

        if(optionalAdminId.isPresent()){

            // find seller with id to modify it's state
            Optional<Seller> optionalSeller = sellerRepository.findById(sellerId);

            if (optionalSeller.isPresent()) {

                Seller existingSeller = optionalSeller.get();

//                remove the non verified seller, from the db

                // mark the existing seller as verified seller
                existingSeller.setVerified(false);



                sellerRepository.delete(existingSeller);

                // notify the seller on email to notify them that they are rejected because of ___ reason

                return true;
            }else {

                throw new UserNotFoundException("Seller with this id not found");
            }

        }else{
            throw new UnauthorizedAccessException("unauthorized access to resource");
        }


    }
    
    // Only admin will able to check un verified sellers
    @Override
   	public List<Seller> getAllUnVerifiedSellers(String jwtToken) {


        Optional<Long> optionalAdminID = jwtUtil.verifyAdmin(jwtToken);

        if(optionalAdminID.isPresent()){
            Long adminID = optionalAdminID.get();
            return sellerRepository.getAllUnVerifiedSellers();

        }else{
            throw new UnauthorizedAccessException("unauthorized");
        }

   		
   	}



    // handle client error if, token is invalid throw UnauthorizedAccessException or throw ClientErrorException
    private Mono<? extends Throwable> handleClientError(ClientResponse clientResponse) {

        // through an error when user password not matched with passed password
        if(clientResponse.statusCode().equals(HttpStatus.UNAUTHORIZED)){
            return Mono.error(new AuthenticationFailedException("password not match"));
        }else if(clientResponse.statusCode().equals(HttpStatus.CONFLICT)) {
        	return Mono.error(new EmailAlreadyExistsException("Provided email already exists"));
        }

        return Mono.error(new ClientErrorException("Client Error: " + clientResponse.statusCode()));

    }
}
