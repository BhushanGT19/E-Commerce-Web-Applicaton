package com.bookcharm.app.service;

import com.bookcharm.app.dto.AddProductDto;
import com.bookcharm.app.exception.ProductNotFoundException;
import com.bookcharm.app.exception.UnauthorizedAccessException;
import com.bookcharm.app.exception.UserNotFoundException;
import com.bookcharm.app.model.Category;
import com.bookcharm.app.model.Product;
import com.bookcharm.app.model.Seller;
import com.bookcharm.app.repository.CategoryRepository;
import com.bookcharm.app.repository.ProductRepository;
import com.bookcharm.app.repository.SellerRepository;
import com.bookcharm.app.utils.FileUtils;
import com.bookcharm.app.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookcharm.app.model.Product;
import com.bookcharm.app.repository.ProductRepository;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductServiceImpl implements ProductService {
	
	

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public List<Product> getAllProducts() {

        return FileUtils.buildProductImages(productRepository.findAll());
    }

    @Override
    public Product getProductById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        return FileUtils.buildProductImage(optionalProduct.get());
    }

    @Override
    public Product addProduct(AddProductDto addProductDto, String jwtToken) {

        // validate the seller and add product in seller products

        Optional<Long> optionalSellerId = jwtUtil.verifySeller(jwtToken);

        if(optionalSellerId.isPresent()){
            Optional<Seller> optionalSeller = sellerRepository.findById(optionalSellerId.get());

            if(optionalSeller.isPresent()){

                Seller seller = optionalSeller.get();

                Product newProduct = new Product();

                // set Book category as by default
                // Book category has id 1 by default
                Optional<Category> optionalCategory = categoryRepository.findById(1l);

                Category category = optionalCategory.get();


                newProduct.setProductName(addProductDto.getProductName());
                newProduct.setProductPrice(addProductDto.getProductPrice());
                newProduct.setProductDescription(addProductDto.getProductDescription());
                newProduct.setAuthor(addProductDto.getAuthor());
                newProduct.setCategory(category);
                newProduct.setIsbn(addProductDto.getIsbn());
                newProduct.setSeller(seller);
                newProduct.setStock(addProductDto.getStock());
                newProduct.setViewCount(0);

                MultipartFile productImage = addProductDto.getProductImage();
                // store the image in folder if not null and assign the stored image name to the product image
                if(productImage != null){
                    String savedProductImageNameInDir = FileUtils.storeImage("products",productImage);
                    newProduct.setProductImage(savedProductImageNameInDir);
                }

                seller.addProduct(newProduct);

                productRepository.save(newProduct);
                sellerRepository.save(seller);

                return newProduct;

            }else{

                throw new UserNotFoundException("Seller not found ");

            }
        }else{
            throw new UnauthorizedAccessException("Unauthorized access to add product");
        }


    }

    @Override
    public Product updateProduct(Long productId, String sellerJwtToken, Product updatedProduct) {
        
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            // Update the existing product with the new information
            existingProduct.setProductName(updatedProduct.getProductName());
            existingProduct.setProductPrice(updatedProduct.getProductPrice());
            existingProduct.setProductDescription(updatedProduct.getProductDescription());
            existingProduct.setProductImage(updatedProduct.getProductImage());
            existingProduct.setStock(updatedProduct.getStock());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setSeller(updatedProduct.getSeller());
            existingProduct.setViewCount(updatedProduct.getViewCount());
            existingProduct.setAuthor(updatedProduct.getAuthor());
            existingProduct.setIsbn(updatedProduct.getIsbn());

            // Save and return the updated product
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public boolean deleteProduct(Long productId, String jwtToken) {

        // validate the seller based on jwtToken
        // verify whether seller has product with ProductId
        // else throw UnAuthorization Exception
    	
    	

        Optional<Long> optionalSellerId = jwtUtil.verifySeller(jwtToken);

        // if seller exists
        if(optionalSellerId.isPresent()){

            Long sellerId = optionalSellerId.get();


            // access the seller with that id
            Optional<Seller> optionalSeller = sellerRepository.findById(sellerId);

            if(optionalSeller.isPresent()){

                Seller seller = optionalSeller.get();

                // find the product with productId
                Optional<Product> optionalProduct = productRepository.findById(productId);

                if(optionalProduct.isPresent()){

                    // validate whether product is related to seller
                    // otherwise throw UnAuthorizedException
                    Product product = optionalProduct.get();

                    // remove product from seller
                    if(!seller.getProducts().remove(product)){
                        throw new UnauthorizedAccessException("Un authorized access");
                    }else{
                        sellerRepository.save(seller);
                        return true;
                    }
                }else{
                    // if product is not found return product not found exception
                    throw new ProductNotFoundException("product with product id not found");
                }
            }else{
                throw new UserNotFoundException("User not found exception");
            }
        }else{
            throw new UserNotFoundException("User not found exception");
        }


    }

    @Override
    public List<Product> getAllProductsOfSeller(String jwtToken){

        Optional<Long> optionalSellerId = jwtUtil.verifySeller(jwtToken);

        if(optionalSellerId.isPresent()){
            Long sellerId = optionalSellerId.get();
            Optional<Seller> optionalSeller = sellerRepository.findById(sellerId);
            if(optionalSeller.isPresent()){
                Seller seller = optionalSeller.get();
//                return all the products by seller
//                // retrieve product images from folder
                List<Product> products = seller.getProducts();

                return FileUtils.buildProductImages(products);
            }else{
                throw new UserNotFoundException("seller with id not found");
            }
        }else{
            throw new UnauthorizedAccessException("unauthorized access exception");
        }
    }
}
