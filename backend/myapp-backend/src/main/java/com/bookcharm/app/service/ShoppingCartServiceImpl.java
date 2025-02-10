package com.bookcharm.app.service;

import com.bookcharm.app.dto.AuthenticationResponse;
import com.bookcharm.app.dto.CartDto;
import com.bookcharm.app.exception.ClientErrorException;
import com.bookcharm.app.exception.UnauthorizedAccessException;
import com.bookcharm.app.model.Product;
import com.bookcharm.app.model.ShoppingCart;
import com.bookcharm.app.model.ShoppingCartProduct;
import com.bookcharm.app.repository.ProductRepository;
import com.bookcharm.app.repository.ShoppingCartRepository;
import com.bookcharm.app.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.webjars.NotFoundException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;


    private WebClient webClient;

    @Value("${url.authentication-service-base-url}")
    final private String authenticationServiceBaseUrl;

    // inject the authentication service url using constructor based dependency injection
    public ShoppingCartServiceImpl(@Value("${url.authentication-service-base-url}") String authenticationServiceBaseUrl, WebClient.Builder webClientBuilder) {
        this.authenticationServiceBaseUrl = authenticationServiceBaseUrl;
        this.webClient = webClientBuilder.baseUrl(authenticationServiceBaseUrl).build();
    }

    @Override
    public Set<ShoppingCartProduct> getShoppingCart(String jwtToken) {

        // first identify the user using jwtToken, then return the shopping cart of user
        Long userId = webClient.post().uri("/user/validate-token").header(HttpHeaders.AUTHORIZATION, jwtToken).retrieve().onStatus(HttpStatus::is4xxClientError, clientResponse ->
                handleClientError(clientResponse)).bodyToMono(AuthenticationResponse.class).map(AuthenticationResponse::getUserId).block();
        Long shoppingCartId = userId;

        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(shoppingCartId);
        if(optionalShoppingCart.isPresent()) {
        	
        	ShoppingCart shoppingCart = optionalShoppingCart.get();

            System.out.println("from getShopping cart serice : " + shoppingCart);

            Set<ShoppingCartProduct> setShoppingCartProduct = new HashSet<>();

            // build the product images for shopping cart products
            shoppingCart.getCartProducts().forEach(shoppingCartProduct  -> {
                shoppingCartProduct.setProduct(FileUtils.buildProductImage(shoppingCartProduct.getProduct()));
            });

        	return shoppingCart.getCartProducts();
        }
        return null;

    }


    @Override
    public ShoppingCart updateShoppingCart(String jwtToken, CartDto cartDto) {

        // authenticate the user using token by making request to authentication service
        // get the user id using token from authentication service

        Long userId = webClient.post().uri("/user/validate-token").header(HttpHeaders.AUTHORIZATION, jwtToken)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> handleClientError(clientResponse))
                .bodyToMono(AuthenticationResponse.class)
                .map(AuthenticationResponse::getUserId)
                .block(); // Blocking call to retrieve the response body

        System.out.println(userId);

        // userId and shopping cart id is same

        Long shoppingCartId = userId;
        Long productId = cartDto.getProductId();
        int quantity = cartDto.getQuantity();

        System.out.println("productId : " + productId);
        System.out.println("quantity " + quantity);
        System.out.println("shoppingCartId : " + shoppingCartId);
        // Retrieve the ShoppingCart entity using shoppingCartId
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(shoppingCartId);

        if (optionalShoppingCart.isPresent()) {
            ShoppingCart shoppingCart = optionalShoppingCart.get();

            // find product using productId
            Optional<Product> optionalProduct = productRepository.findById(productId);

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                // Check if the ShoppingCart contains a ShoppingCartProduct with productId
                Optional<ShoppingCartProduct> optionalShoppingCartProduct = shoppingCart.getCartProducts().stream()
                        .filter(cartProduct ->
                                cartProduct.getProduct().getProductId().equals(product.getProductId())).findFirst();


                if (optionalShoppingCartProduct.isPresent()) {
                    // If ShoppingCart contains ShoppingCartProduct with productId and correct shoppingCartId, update its quantity with new quantity
                    ShoppingCartProduct optionalShoppingCartProductToUpdate = optionalShoppingCartProduct.get();
                    optionalShoppingCartProductToUpdate.setQuantity(quantity);
                } else {
                    // If ShoppingCart does not contain ShoppingCartProduct with productId and correct shoppingCartId, create a new one

                    ShoppingCartProduct newProduct = new ShoppingCartProduct();
                    newProduct.setProduct(product);
                    newProduct.setQuantity(quantity);
                    newProduct.setShoppingCart(shoppingCart);
                    shoppingCart.addCartProduct(newProduct);


                   
                }
                
                shoppingCartRepository.save(shoppingCart);
            }
            else {
                // Handle case where Product with productId does not exist
                throw new NotFoundException("Product Not Found");
            }

            return shoppingCart;
        }
        else {

            // case where cart with shoppingcartid does not exist
            throw new NotFoundException("Cart Not Found");
        }
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
