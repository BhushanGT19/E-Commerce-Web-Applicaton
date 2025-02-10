package com.bookcharm.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AdminLoginValidationDto {

    Long adminId;
    String passWord;
    String validationPassWord;
}
