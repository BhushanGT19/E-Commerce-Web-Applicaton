package com.bookcharm.app.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bookcharm.app.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Add custom query methods if needed
    // For example, finding a payment by Razorpay payment ID
    Payment findByRazorpayPaymentId(String razorpayPaymentId);
}
