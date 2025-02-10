package com.bookcharm.app.dto;

import com.bookcharm.app.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class RegistrationResponse {
    User user;
    String token;
}
