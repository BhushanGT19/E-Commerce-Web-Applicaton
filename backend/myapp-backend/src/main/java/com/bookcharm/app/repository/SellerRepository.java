package com.bookcharm.app.repository;

import com.bookcharm.app.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    // Additional seller-specific query methods if needed
    Optional<Seller> findByEmail(String email);
    @Query("SELECT s FROM Seller s WHERE s.isVerified = false")
    List<Seller> getAllUnVerifiedSellers();
}
