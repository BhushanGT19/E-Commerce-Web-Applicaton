package com.bookcharm.app.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookcharm.app.model.Order;
import com.bookcharm.app.service.PaymentService;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // existing code

//    @PostMapping("/create-order/{orderId}")
//    public ResponseEntity<String> createOrder(@PathVariable Long orderId) throws RazorpayException {
//        Order order = paymentService.getOrderById(orderId);
//        if (order != null) {
//        	System.out.println("Eoor");
//            String razorpayOrderId = paymentService.createRazorpayOrder(order);
//            System.out.println("kkk");
//            return new ResponseEntity<>(razorpayOrderId, HttpStatus.OK);
//        } else {
// 
//            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
//        }
//    }


    @PostMapping("/payment-callback")
    public void handlePaymentCallback(@RequestBody String payload) throws RazorpayException {
        // Handle Razorpay payment callback (webhook) here
        // Validate the payload and update your database accordingly
        // Ensure secure handling to prevent fraud
        JSONObject jsonPayload = new JSONObject(payload);
        String razorpayOrderId = jsonPayload.getString("order_id");
        String paymentId = jsonPayload.getString("payment_id");
        double amount = jsonPayload.getDouble("amount");

        paymentService.handlePaymentCallback(razorpayOrderId, paymentId, amount);
    }
}
