package com.bookcharm.app.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "addressId")
    private Long orderId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Payment payment;
    
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @JoinColumn(name = "orderStatus")
    private String orderStatus;

    @JoinColumn(name = "totalAmount")
    private double totalAmount;

    @JoinColumn(name = "orderDate")
    private LocalDate orderDate;

    @JoinColumn(name = "quantity")
    private int quantity;


    public Order() {
		super();
	}

	public Order(Long orderId, User user, Payment payment, Product product, String orderStatus, double totalAmount,
			LocalDate orderDate, int quantity) {
		super();
		this.orderId = orderId;
		this.user = user;
		this.payment = payment;
		this.product = product;
		this.orderStatus = orderStatus;
		this.totalAmount = totalAmount;
		this.orderDate = orderDate;
		this.quantity = quantity;
	}

    // Getters and Setters
    public Long getOrderId() {
        return orderId;
    }

	public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = LocalDate.parse(orderDate);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", user=" + user + ", payment=" + payment + ", product=" + product
				+ ", orderStatus=" + orderStatus + ", totalAmount=" + totalAmount + ", orderDate=" + orderDate
				+ ", quantity=" + quantity + "]";
	}
    

}
