package com.bookcharm.app.service;


public class RazorpayApiResponse {

    private String razorpayPaymentId;
    private String razorpayUrl;

    public RazorpayApiResponse(String razorpayPaymentId, String razorpayUrl) {
        this.razorpayPaymentId = razorpayPaymentId;
        this.razorpayUrl = razorpayUrl;
    }

    public String getRazorpayPaymentId() {
        return razorpayPaymentId;
    }

    public String getRazorpayUrl() {
        return razorpayUrl;
    }
}
