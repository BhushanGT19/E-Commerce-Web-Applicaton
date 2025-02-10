package com.bookcharm.app.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shoppingcart")
@ToString
public class ShoppingCart {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "cartId")
    @JsonIgnore
    private User user;

    // Other shopping cart attributes and methods

    // Getters and setters
    
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<ShoppingCartProduct> cartProducts = new HashSet<>();

   

    public Set<ShoppingCartProduct> getCartProducts(){
        return cartProducts;
    }
    
    // constructors

    // methods
    public void addCartProduct(ShoppingCartProduct shoppingCartProduct){
        this.cartProducts.add(shoppingCartProduct);
    }

    public void removeCartProduct(ShoppingCartProduct shoppingCartProduct){
        this.cartProducts.remove(shoppingCartProduct);
    }
}