package com.bookcharm.app.dto;

import java.time.LocalDate;

import com.bookcharm.app.model.Product;

public class OrderDTO {

    private Long userId;
    private Long productId;
    double totalAmount;
    private String orderStatus;
    private LocalDate orderDate;
    private int quantity;
    private String receipt;  // New field for receipt information

    // Constructors
    public OrderDTO() {
    }

    public OrderDTO(Long userId, Long productId, double totalAmount, String orderStatus, LocalDate orderDate, int quantity, String receipt) {
        this.userId = userId;
        this.productId = productId;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.quantity = quantity;
        this.receipt = receipt;
    }

    // Getters and Setters
    // (Existing getters and setters...)

    public String getReceipt() {
        return receipt;
    }

    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setReceipt(String receipt) {
        this.receipt = receipt;
    }
}
