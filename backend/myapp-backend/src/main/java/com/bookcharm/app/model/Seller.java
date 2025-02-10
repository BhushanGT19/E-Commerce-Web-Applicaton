package com.bookcharm.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import javax.persistence.*;
import javax.websocket.ClientEndpoint;

@Entity
@Table(name = "Sellers")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "sellerId")
    private Long sellerId;
    @JoinColumn(name = "firstName")
    private String firstName;
    @JoinColumn(name = "lastName")
    
    private String lastName;
    @JoinColumn(name = "panNumber")
    private String panNumber;
    @JoinColumn(name = "mobileNumber")
    private String mobileNumber;
    @JoinColumn(name = "email")
    private String email;

    @JsonIgnore
    @JoinColumn(name = "passWord")
    private String passWord;
    


    @JoinColumn(name = "isVerified")
    private boolean isVerified;

    @JsonIgnore
    @OneToMany(mappedBy = "seller",cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Product> products;

    // Constructors
    public Seller() {
    }

    public Seller(String firstName, String lastName, String panNumber, String mobileNumber, String email, String passWord, boolean isVerified) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.panNumber = panNumber;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.passWord = passWord;
        this.isVerified = isVerified;
    }

    // Getters and Setters
    public Long getSellerId() {
        return sellerId;
    }



    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public List<Product> getProducts(){
        return this.products;
    }

    public void setProducts(List<Product> products){
        this.products = products;
    }

    public void addProduct(Product product){
        this.products.add(product);
    }

    public void removeProduct(Product product){
        this.products.remove(product);
    }

    @Override
    public String toString() {
        return "Seller{" +
                "sellerId=" + sellerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", panNumber='" + panNumber + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", email='" + email + '\'' +
                ", passWord='" + passWord + '\'' +
                ", isVerified='" + isVerified + '\'' +
                '}';
    }
}
