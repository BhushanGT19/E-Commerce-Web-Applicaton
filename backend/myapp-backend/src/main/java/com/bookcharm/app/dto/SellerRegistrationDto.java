package com.bookcharm.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellerRegistrationDto {

    String firstName;
    String lastName;
    String email;
    String passWord;
    String panNumber;
    String mobileNumber;
}
