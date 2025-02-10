package com.bookcharm.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "Admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "adminId")
    private Long adminId;

    @JoinColumn(name = "userName")
    private String userName;

    @JsonIgnore
    @JoinColumn(name = "passWord")
    private String passWord;

    @OneToOne
    @JoinColumn(name = "addressId")
    private Address address;


    // Constructors
    public Admin() {
    }

    public Admin(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    // Getters and Setters
    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    // Other methods, if needed

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", userName='" + userName + '\'' +
                ", password='" + passWord + '\'' +
                '}';
    }
}
