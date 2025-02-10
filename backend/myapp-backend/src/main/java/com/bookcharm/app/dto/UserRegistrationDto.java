package com.bookcharm.app.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRegistrationDto {
    private String firstName;
    private String lastName;
    private String email;
    private String passWord;
    private String mobileNumber;
    private String profileUrl;
}
