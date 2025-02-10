package com.bookcharm.app.model;

import javax.persistence.*;

@Entity
@Table(name = "Addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "addressId")
    private Long addressId;

    @JoinColumn(name = "city")
    private String city;
    @JoinColumn(name = "state")
    private String state;
    @JoinColumn(name = "country")
    private String country;
    @JoinColumn(name = "postalCode")
    private String postalCode;
    @JoinColumn(name = "landmark")
    private String landmark;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    // Constructors
    public Address() {
    }

    public Address(String city, String state, String country, String postalCode, String landmark, User user) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
        this.landmark = landmark;
        this.user = user;
    }

    // Getters and Setters
    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Other methods, if needed

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", landmark='" + landmark + '\'' +
                '}';
    }
}
