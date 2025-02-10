package com.bookcharm.app.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddProductDto{

    private String productName;
    private BigDecimal productPrice;
    private String productDescription;
    private MultipartFile productImage;
    private String category;
    private int stock;
    private String author;
    private String isbn;
}

