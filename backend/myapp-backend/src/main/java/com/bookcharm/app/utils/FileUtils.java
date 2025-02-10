package com.bookcharm.app.utils;

import com.bookcharm.app.exception.ProductNotFoundException;
import com.bookcharm.app.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;

public class FileUtils {


    public static String storeImage(String rootDirName, MultipartFile file){

        try {
            if(file.isEmpty()) {
                throw new RuntimeException("Empty file");
            }

            // Get the current time in milliseconds
            long currentTimeMillis = Calendar.getInstance().getTimeInMillis();
            // Generate the filename using the current time in milliseconds
            String fileName = currentTimeMillis + "_" + file.getOriginalFilename();

            Path destination = Paths.get(rootDirName).resolve(fileName).normalize().toAbsolutePath();
            System.out.println(destination);

            InputStream  is= file.getInputStream();
            Files.copy(is, destination);

            is.close();
            return fileName;

        } catch(IOException e) {
            e.printStackTrace();
            throw new RuntimeException("empty store");
        }

    }

    public static byte[] retrieveImage(String rootDirName, String fileName){

        Path file = Paths.get(rootDirName).normalize().toAbsolutePath();

        Path rootPath = Paths.get(rootDirName).normalize().toAbsolutePath();
        Path filePath = rootPath.resolve(fileName).normalize();

        System.out.println(filePath);

        try {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while retrieving product image");
            // if no image present
            return null;
        } 

    }

    // this function converts the product images stored in folders into the Base64 format for retrieval

    public static List<Product> buildProductImages(List<Product> products){

        for(Product p : products){

            System.out.println(p);
           
            try {
                if(p.getProductImage() != null){
                    String encodedString = Base64.getEncoder().encodeToString(FileUtils.retrieveImage("products", p.getProductImage()));
                    p.setProductImage(encodedString);
            }    
            }     
            catch (Exception e) {
                 e.printStackTrace();
                 System.out.println("error while creating string of image, image may not be exist or bytes return of image may be null");
            }
        
            
        }

        
        return products;
    }

    public static Product buildProductImage(Product product){
        String encodedString = "data:image/jpeg;base64, " + Base64.getEncoder().encodeToString(FileUtils.retrieveImage("products", product.getProductImage()));
        product.setProductImage(encodedString);
        return product;
    }
}
