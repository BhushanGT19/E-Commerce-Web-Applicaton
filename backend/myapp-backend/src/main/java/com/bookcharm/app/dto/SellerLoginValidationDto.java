package com.bookcharm.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellerLoginValidationDto {

    Long sellerId;
    String sellerPassWord;
    String validationPassWord;
}