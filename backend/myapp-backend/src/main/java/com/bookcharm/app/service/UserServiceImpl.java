package com.bookcharm.app.service;

import com.bookcharm.app.dto.*;
import com.bookcharm.app.exception.*;
import com.bookcharm.app.model.Address;
import com.bookcharm.app.model.Order;
import com.bookcharm.app.model.ShoppingCart;
import com.bookcharm.app.model.User;
import com.bookcharm.app.repository.UserRepository;
import com.bookcharm.app.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtUtil jwtUtil;

    private WebClient.Builder builder;

    final private WebClient authenticationServiceWebClient;
    final private WebClient mailServiceWebClient;

    public UserServiceImpl(@Value("${url.authentication-service-base-url}") String authenticationServiceBaseUrl, @Value("${url.notification-service-base-url}") String notificationServiceBaseUrl,  WebClient.Builder builder) {
        this.builder = builder;
        this.authenticationServiceWebClient = builder.baseUrl(authenticationServiceBaseUrl).build();
        this.mailServiceWebClient = builder.baseUrl(notificationServiceBaseUrl).build();
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }


    // register the user

    @Override
    public RegistrationResponse createUser(UserRegistrationDto userRegistrationDto) {


        // validate mobile number

        Optional<User> optionalUser = userRepository.findByEmail(userRegistrationDto.getEmail());

        if (optionalUser.isPresent()) {
            // throw error as user with email already exists
            User user = optionalUser.get();

            throw new EmailAlreadyExistsException("Email address is already in use");
        } else {
            // means user with email address does not exists
            // next steps
            // validate the user details
            // email,
            // call authentication-mail service to create as well as get the User, and token if valid

            RegistrationApiResponse registrationApiResponse = authenticationServiceWebClient.post().uri("/user/register")
                    .body(BodyInserters.fromValue(userRegistrationDto))
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, clientResponse -> handleClientError(clientResponse))
                    .bodyToMono(RegistrationApiResponse.class)
                    .block();


            // using registrationApiResponse
            // create, new User
            // send , newUser Token
            // send email to the user for registering on platform using authentication-mail service


            UserRegistrationDto newUserRegistrationDto = registrationApiResponse.getUserRegistrationDto();
            User newUser = new User();
            newUser.setEmail(newUserRegistrationDto.getEmail());
            newUser.setFirstName(newUserRegistrationDto.getFirstName());
            newUser.setLastName(newUserRegistrationDto.getLastName());
            newUser.setMobileNumber(newUserRegistrationDto.getMobileNumber());
            newUser.setProfileUrl(newUserRegistrationDto.getProfileUrl());
            newUser.setPassWord(newUserRegistrationDto.getPassWord());

            // create shopping cart for newUser
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUser(newUser); // Set the user for the shopping cart
            newUser.setShoppingCart(shoppingCart); // Set the shopping cart for the user


            // persist the user in db
            newUser = userRepository.save(newUser);


            // login the current user using newUser id generated with and return token
            LoginValidationDto loginValidationDto = new LoginValidationDto();
            loginValidationDto.setUserId(newUser.getUserId());
            loginValidationDto.setUserPassword(newUser.getPassWord());
            loginValidationDto.setValidationPassword(userRegistrationDto.getPassWord());

            String jwtToken = authenticationServiceWebClient.post().uri("/user/login").body(BodyInserters.fromValue(loginValidationDto)).retrieve().onStatus(HttpStatus::is4xxClientError,clientResponse ->  handleClientError(clientResponse)).bodyToMono(LoginResponse.class).map(LoginResponse::getToken).block();

            // send email to user

            // .subscribe() allows us to send non-blocking request to the another server
            try{
                mailServiceWebClient.post().uri("/user/send-welcome").body(BodyInserters.fromValue(newUser)).retrieve().bodyToMono(Void.class).subscribe(response -> System.out.println(response), error -> System.out.println(error));
            }catch (Exception e){
                e.printStackTrace();
            }


            return new RegistrationResponse(newUser, jwtToken);

        }

    }

    // authenticate (login) of user

    @Override
    public LoginResponse loginUser(UserLoginDto userLoginDto){

        // find whether user with email exists or not

        String email = userLoginDto.getEmail();
        String password = userLoginDto.getPassWord();

        // if user not exists
        // return error with message "user not exists with this email"
        // if exists
        // send user for authentication with userLoginDto to compare the passwords and get the token
        //      if error occured means password didn't match
        //      else
        //      send the LoginResponse with (User and token)


        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isPresent()){

            User user = optionalUser.get();
            LoginValidationDto loginValidationDto = new LoginValidationDto();
            loginValidationDto.setUserId(user.getUserId());
            loginValidationDto.setUserPassword(user.getPassWord());
            loginValidationDto.setValidationPassword(userLoginDto.getPassWord());

            String jwtToken = authenticationServiceWebClient.post().uri("/user/login").body(BodyInserters.fromValue(loginValidationDto)).retrieve().onStatus(HttpStatus::is4xxClientError,clientResponse ->  handleClientError(clientResponse)).bodyToMono(LoginResponse.class).map(LoginResponse::getToken).block();
            // if everything is fine return response
            return new LoginResponse(user, jwtToken);

        }else{
            // if user not exists
            throw new UserNotFoundException("User with " + email + " not found");
        }

    }


    @Override
    public User updateUser(Long userId, User user) {
        if (userRepository.existsById(userId)) {
            user.setUserId(userId); // Ensure the provided user object has the correct ID
            return userRepository.save(user);
        }
        return null; // User with the given ID does not exist
    }

    @Override
    public boolean deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false; // User with the given ID does not exist
    }

//    @Override
//    public void addToCart(Long userId, Long productId, Integer quantity) {
//        User user = userRepository.findById(userId).orElse(null);
//
//        if (user != null) {
//            // Implement logic to add the specified product to the user's cart
//            // For simplicity, assume user has a single cart
//            // You might need to handle scenarios with multiple carts differently
//
//            // Assume user has a method like getShoppingCart() to get the cart
//            ShoppingCart cart = user.getShoppingCart();
//
//            // Check if the product is already in the cart
//            Optional<Product> existingProduct = cart.getProducts().stream()
//                    .filter(product -> product.getProductId().equals(productId))
//                    .findFirst();
//
//            if (existingProduct.isPresent()) {
//                // Product is already in the cart, update quantity
//                Product product = existingProduct.get();
//                product.setStock(product.getStock() + quantity);
//            } else {
//                // Product is not in the cart, create a new entry
//                Product newProduct = new Product(); // Instantiate Product with proper values
//                newProduct.setProductId(productId);
//                newProduct.setStock(quantity);
//
//                // Add the new product to the cart
//                cart.getProducts().add(newProduct);
//            }
//
//            // Save the updated user with the modified cart
//            userRepository.save(user);
//        }
//    }
//
//    @Override
//    public void removeFromCart(Long userId, Long productId) {
//        User user = userRepository.findById(userId).orElse(null);
//
//        if (user != null) {
//            // Implement logic to remove the specified product from the user's cart
//            // For simplicity, assume user has a single cart
//            // You might need to handle scenarios with multiple carts differently
//
//            // Assume user has a method like getShoppingCart() to get the cart
//            ShoppingCart cart = user.getShoppingCart();
//
//            // Remove the product from the cart
//            cart.getProducts().removeIf(product -> product.getProductId().equals(productId));
//
//            // Save the updated user with the modified cart
//            userRepository.save(user);
//        }
//    }
    
   
    @Override
	public Set<Order> getAllOrdersOfUsers(String authorization) {
		
    	Optional<Long> userId = jwtUtil.verifyUser(authorization);
    	if(userId.isPresent()) {
    		Optional<User> user = userRepository.findById(userId.get());
    		if(user.isPresent()) {
    			Set<Order> orders = user.get().getOrders();
    			return orders;
    		}else {
    			throw new UserNotFoundException("USER NOT FOUND");
    		}
    	}else {
    		throw new UnauthorizedAccessException("UNAUTHORIZE");
    	}
		
	}
    	
    	
    	
    	
    

    // handle client error if, token is invalid throw UnauthorizedAccessException or throw ClientErrorException
    private Mono<? extends Throwable> handleClientError(ClientResponse clientResponse) {

        // through an error when user password not matched with passed password
        if(clientResponse.statusCode().equals(HttpStatus.UNAUTHORIZED)){
            return Mono.error(new AuthenticationFailedException("password not match"));
        }

        if(clientResponse.statusCode().equals(HttpStatus.CONFLICT)){
            return Mono.error(new InvalidEmailException("Invalid email provided"));
        }

        return Mono.error(new ClientErrorException("Client Error: " + clientResponse.statusCode()));

    }

	
}
