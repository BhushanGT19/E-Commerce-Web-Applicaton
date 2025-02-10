package com.bookcharm.app.service;

import com.bookcharm.app.dto.SellerLoginDto;

import com.bookcharm.app.dto.SellerRegistrationDto;
import com.bookcharm.app.dto.SellerResponse;
import com.bookcharm.app.model.Seller;

import java.util.List;

public interface SellerService {
    Seller getSellerById(Long sellerId);
    Seller createSeller(SellerRegistrationDto sellerRegistrationDto);
    Seller verifySeller(Long sellerId, String adminJwtToken);
    boolean deleteSeller(Long sellerId, String adminJwtToken);
    SellerResponse loginSeller(SellerLoginDto sellerLoginDto);
    // Other SellerService methods
	List<Seller> getAllUnVerifiedSellers(String jwtToken);
}
