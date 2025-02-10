package com.bookcharm.app.service;

import com.bookcharm.app.model.Order;
import com.bookcharm.app.model.Payment;
import com.bookcharm.app.repository.OrderRepository;
import com.bookcharm.app.repository.PaymentRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.razorpay.*;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

//    public RazorpayApiResponse createRazorpayOrder(Order order) throws RazorpayException {
//        // Initialize Razorpay client
//        RazorpayClient razorpayClient = new RazorpayClient("rzp_test_FQ4cpgtlLQsLA9", "ZN8meANHqHoBZsneaVNYASWc");
//
//        // Create order request
//        JSONObject orderRequest = new JSONObject();
//        orderRequest.put("amount", (int) (order.getTotalAmount() * 100)); // Amount in paise
//        orderRequest.put("currency", "INR");
//        orderRequest.put("receipt", "order_rcptid_" + System.currentTimeMillis());
//        orderRequest.put("payment_capture", 1);
//
//        // Create order
//        com.razorpay.Order razorpayOrder = razorpayClient.orders.create(orderRequest);
//
//        // Extract relevant information from the Razorpay API response
//        String razorpayPaymentId = razorpayOrder.get("id");
//        String razorpayUrl = razorpayOrder.get("razorpayUrl"); // Please check Razorpay documentation for the correct field name
//
//        return new RazorpayApiResponse(razorpayPaymentId, razorpayUrl);
//    }

    public boolean captureRazorpayPayment(String paymentId, double amount) throws RazorpayException {
        // Initialize Razorpay client
        RazorpayClient razorpayClient = new RazorpayClient("rzp_test_FQ4cpgtlLQsLA9", "ZN8meANHqHoBZsneaVNYASWc");

        // Capture payment request
        JSONObject captureRequest = new JSONObject();
        captureRequest.put("amount", (int) (amount * 100)); // Amount in paise

        // Capture payment
        com.razorpay.Payment payment = razorpayClient.payments.capture(paymentId, captureRequest);

        // Check if payment capture is successful
        return "captured".equals(payment.get("status"));
    }

    public void savePayment(Payment payment) {
        paymentRepository.save(payment);
    }

    public void handlePaymentCallback(String razorpayOrderId, String paymentId, double amount) throws RazorpayException {
        // Fetch the order from the database using razorpayOrderId
        Optional<Order> optionalOrder = orderRepository.findById(Long.parseLong(razorpayOrderId));

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            // Update the order status or perform any other necessary actions
            order.setOrderStatus("PAID");

            // Save the updated order
            orderRepository.save(order);

            // Save payment details to the database
            Payment payment = new Payment();
            payment.setOrder(order);
            payment.setRazorpayPaymentId(paymentId);
            payment.setAmount(amount);
            payment.setCurrency("INR");
            payment.setStatus("PAID");

            // Save payment
            savePayment(payment);

            // Optionally, capture the payment if required
            // captureRazorpayPayment(paymentId, amount);
        }
    }

    public Order getOrderById(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        return optionalOrder.orElse(null);
    }
}
