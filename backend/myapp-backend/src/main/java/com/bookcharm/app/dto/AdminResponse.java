package com.bookcharm.app.dto;

import com.bookcharm.app.model.Admin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponse {

    Admin admin;
    String token;
}
