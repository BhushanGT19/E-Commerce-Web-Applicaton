package com.bookcharm.app.service;

import java.util.Set;

import com.bookcharm.app.dto.CartDto;
import com.bookcharm.app.model.ShoppingCart;
import com.bookcharm.app.model.ShoppingCartProduct;

public interface ShoppingCartService {
	Set<ShoppingCartProduct> getShoppingCart(String jwtToken);
    ShoppingCart updateShoppingCart(String jwtToken, CartDto cartDto);
}
