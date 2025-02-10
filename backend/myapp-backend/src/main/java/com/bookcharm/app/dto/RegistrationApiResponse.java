package com.bookcharm.app.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationApiResponse {
    UserRegistrationDto userRegistrationDto;
    String token;
}
