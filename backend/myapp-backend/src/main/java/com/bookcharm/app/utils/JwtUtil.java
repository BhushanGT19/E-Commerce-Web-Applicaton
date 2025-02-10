package com.bookcharm.app.utils;

import com.bookcharm.app.dto.*;
import com.bookcharm.app.exception.ClientErrorException;
import com.bookcharm.app.exception.UnauthorizedAccessException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;


@NoArgsConstructor // important
@Service
public class JwtUtil {


	@Autowired
	private WebClient.Builder builder;

 	// @Value auto wiring the application.properties key value pairs
	// these dependencies are autowired after object of class is created

	@Value("${url.authentication-service-base-url}")
	private String authenticationServiceBaseUrl;

	@Value("${url.notification-service-base-url}")
	private String notificationServiceBaseUrl;

	private WebClient authenticationServiceWebClient;

	public Optional<Long> verifyUser(String jwt) {

		try{
			
			Long userID = getAuthenticationServiceWebClient().post().uri("/user/validate-token").header(HttpHeaders.AUTHORIZATION, jwt).retrieve().onStatus(HttpStatus::is4xxClientError, clientResponse -> handleClientError(clientResponse)).bodyToMono(UserValidationResponseDto.class).map((UserValidationResponseDto::getUserId)).block();
			return Optional.of(userID);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

		// if no userId found
		return null;
		
	}
	public Optional<Long> verifySeller(String jwt) {

//		SellerValidationDto sellerValidationDto = new SellerValidationDto();
//		sellerValidationDto.setToken(jwt);

		System.out.println("from verify seller" + jwt);
		try{
			Long sellerId = getAuthenticationServiceWebClient().post().uri("/seller/validate-token").header(HttpHeaders.AUTHORIZATION, jwt).retrieve().onStatus(HttpStatus::is4xxClientError, clientResponse -> handleClientError(clientResponse)).bodyToMono(SellerValidationResponseDto.class).map((SellerValidationResponseDto::getSellerId)).block();
			return Optional.of(sellerId);
		}catch(Exception e){
			System.out.println(e.getMessage());
//			throw e;
		}

		// if no sellerId found
		return null;
		
	}
	public  Optional<Long> verifyAdmin(String jwt) {

		try{
			Long adminId = getAuthenticationServiceWebClient().post().uri("/admin/validate-token").header(HttpHeaders.AUTHORIZATION, jwt).retrieve().onStatus(HttpStatus::is4xxClientError, clientResponse -> handleClientError(clientResponse)).bodyToMono(AdminValidationResponseDto.class).map((AdminValidationResponseDto::getAdminId)).block();
			return Optional.of(adminId);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

		// if no adminId found
		return null;

	}

	// follow the singleton principle
	private WebClient getAuthenticationServiceWebClient(){
		return authenticationServiceWebClient == null ? builder.baseUrl(authenticationServiceBaseUrl).build() : authenticationServiceWebClient;

	}


	// handle client error if, token is invalid throw UnauthorizedAccessException or throw ClientErrorException
	private Mono<? extends Throwable> handleClientError(ClientResponse clientResponse) {

//        System.out.println(clientResponse.body);
		if (clientResponse.statusCode().equals(HttpStatus.UNAUTHORIZED)) {
			// Handle 401 Unauthorized error
			return Mono.error(new UnauthorizedAccessException("Unauthorized access"));
		} else {
			// Handle other 4xx errors
			return Mono.error(new ClientErrorException("Client Error: " + clientResponse.statusCode()));
		}

	}

}
