package com.bookcharm.app.controller;

import com.bookcharm.app.dto.AddProductDto;
import com.bookcharm.app.exception.AuthenticationFailedException;
import com.bookcharm.app.exception.ProductNotFoundException;
import com.bookcharm.app.exception.UserNotFoundException;
import com.bookcharm.app.utils.JwtUtil;
import com.bookcharm.app.model.Product;
import com.bookcharm.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    


    //Seller will add Product
    //Annotation @ModelAttribute is used when the requested content-type is multipart/form-data
    //this annotation is used to map with the dto object in multipart/form-data
    //unlike @RequestBody which map to dto object when the content-type is JSON or XML, it translates the JSON object into Java object (Unmarshalling using JACKSON)


    @PostMapping
    public ResponseEntity<?> addProduct(@ModelAttribute AddProductDto addProductDto, @RequestHeader String Authorization) {

    	try {
            productService.addProduct(addProductDto, Authorization);
            return ResponseEntity.ok("Product Added SuccessFully");
        }catch (AuthenticationFailedException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication Failed " + ex.getMessage());
        }


    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestHeader String Authorization , @RequestBody Product product) {
        // Add logic for updating an existing product
    	Product updatedProduct = productService.updateProduct(productId, Authorization, product);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
    } else {
        return ResponseEntity.notFound().build();
    }
    }
    
    
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId,@RequestHeader String Authorization) {

        try {
            productService.deleteProduct(productId, Authorization);
            return ResponseEntity.ok("Product Deleted SuccessFully");
        }catch (UserNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found " + ex.getMessage());
        }catch (AuthenticationFailedException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication Failed " + ex.getMessage());
        }catch (ProductNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with given doesn't exist");
        }

    }

    @GetMapping("/seller")
    public ResponseEntity<?> getAllProductsOfSeller(@RequestHeader String Authorization){

            List<Product> sellerProducts = productService.getAllProductsOfSeller(Authorization);
            return new ResponseEntity<>(sellerProducts, HttpStatus.OK);

    }

}
