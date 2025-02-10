package com.bookcharm.app.dto;

import com.bookcharm.app.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UpdateUserDto {
    private User user;
    private MultipartFile image;


}
