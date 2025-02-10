package com.bookcharm.app.controller;

import java.time.LocalDate;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookcharm.app.dto.OrderDTO;
import com.bookcharm.app.model.Order;
import com.bookcharm.app.model.Payment;
import com.bookcharm.app.service.OrderService;
import com.bookcharm.app.service.PaymentService;
import com.bookcharm.app.service.RazorpayApiResponse;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private PaymentService paymentService;
    
   
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Order order = OrderService.getOrderById(orderId);
        if (order != null) {
        	System.out.println("ok");
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @PostMapping
//   // public ResponseEntity<Order> createOrder(@RequestBody Order order, @RequestHeader String Authorization) 
//    public ResponseEntity<Order> createOrder(@RequestBody Order order){
//
//    	
//        // Add logic for creating a new order
//        Order createdOrder = orderService.createOrder(order);
//        
//        return ResponseEntity.ok(createdOrder);
//        
//    }

//    @PostMapping("/create-and-pay")
//    public ResponseEntity<String> createAndPayOrder(@RequestBody Order order) {
//        try {
//            // Add logic for creating a new order
//            Order createdOrder = orderService.createOrder(order);
//
//            if (createdOrder != null) {
//                // Call the createRazorpayOrder method during order creation
//                RazorpayApiResponse razorpayApiResponse = paymentService.createRazorpayOrder(createdOrder);
//
//                // Redirect the user to the Razorpay payment interface
//                return new ResponseEntity<>(razorpayApiResponse.getRazorpayUrl(), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("Failed to create order", HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>("Error creating and paying order: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    
    @PostMapping("/create-razorpay-order")
    public ResponseEntity<String> createRazorpayOrder(@RequestBody OrderDTO orderDTO) {
        try {
            // Initialize Razorpay client
            RazorpayClient razorpay = new RazorpayClient("rzp_test_FQ4cpgtlLQsLA9", "ZN8meANHqHoBZsneaVNYASWc");

            // Create order request
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", (int) (orderDTO.getTotalAmount() * 100)); // Amount in paise
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", orderDTO.getReceipt() + System.currentTimeMillis());
           // orderRequest.put("payment_capture", 1);

            // Create Razorpay order
            com.razorpay.Order order = razorpay.orders.create(orderRequest);
            //////////////////////////////////////////////////
            
//            Order orderdb = new Order();
//            orderdb.setUserId(orderDTO.getUserId());
//            orderdb.setProductId(orderDTO.getProductId());
//            orderdb.setTotalAmount(orderDTO.getTotalAmount());
//            orderdb.setOrderStatus("PENDING"); 
//            orderdb.setOrderDate(LocalDate.now().toString());
//            orderdb.setQuantity(orderDTO.getQuantity());
//            
//            orderService.createOrder(orderdb);
//            
//            
//            
//            Payment payment = new Payment();
//            payment.setOrder(orderdb);
//            payment.setRazorpayPaymentId(order.get("id").toString());
//            payment.setAmount(orderDTO.getTotalAmount());
//            payment.setCurrency("INR");
//            payment.setStatus("PENDING"); // Set the initial status as needed
//
//            // Save the payment
//            paymentService.savePayment(payment);
            //////////////////////////////////////////////////
            System.out.println("ordDto" + orderDTO);
            System.out.println("receipt" + orderDTO.getReceipt() + System.currentTimeMillis());
            // Return Razorpay order ID
            return new ResponseEntity<>(order.get("id").toString(), HttpStatus.OK);

        } catch (RazorpayException e) {
            return new ResponseEntity<>("Error creating Razorpay order: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


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
    
    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody Order order) {
        // Add logic for updating an existing order
        Order updatedOrder = orderService.updateOrder(orderId, order);
        if (updatedOrder != null) {
            return ResponseEntity.ok(updatedOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        // Added logic for deleting an order
        boolean deleted = orderService.deleteOrder(orderId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Other OrderController methods
}
