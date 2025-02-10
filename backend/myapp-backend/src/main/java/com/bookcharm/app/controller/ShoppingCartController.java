package com.bookcharm.app.controller;

import com.bookcharm.app.dto.CartDto;
import com.bookcharm.app.exception.ClientErrorException;
import com.bookcharm.app.exception.UnauthorizedAccessException;
import com.bookcharm.app.model.ShoppingCart;
import com.bookcharm.app.model.ShoppingCartProduct;
import com.bookcharm.app.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/shopping-carts")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping
    public ResponseEntity<Set<ShoppingCartProduct> > getShoppingCartByUserId(@RequestHeader String Authorization) {

        String jwtToken = Authorization;
        System.out.println("jwtToken using request header : " + Authorization);

        Set<ShoppingCartProduct> shoppingCart = shoppingCartService.getShoppingCart(jwtToken);

        System.out.println(shoppingCart);

        // return the status accordingly, if user is not authorized
        return shoppingCart != null ? new ResponseEntity<>(shoppingCart, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @PutMapping
    public ResponseEntity<Void> updateShoppingCart(@RequestHeader String Authorization, @RequestBody CartDto cartDto){


        String jwtToken = Authorization;
      
        System.out.print("This is quantity" + cartDto.getQuantity());
        System.out.print("This is productId" + cartDto.getProductId());


        try{
            shoppingCartService.updateShoppingCart(jwtToken, cartDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(UnauthorizedAccessException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }catch (ClientErrorException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
