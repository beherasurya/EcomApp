package com.app.ecom.service;

import com.app.ecom.dto.CartItermRequest;
import com.app.ecom.model.CartItem;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import com.app.ecom.repository.CartItemRepository;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public boolean addToCart(String userId, CartItermRequest request) {

        Optional<Product> optProduct =  productRepository.findById(request.getProductId());
        if(optProduct.isEmpty())
                    return false;
        Product product = optProduct.get();
        if(product.getStockQuantity() < request.getQuantity())
            return false;

        Optional<User>  userOpt = userRepository.findById(Long.valueOf(userId));
        if(userOpt.isEmpty())
            return false;
        User user = userOpt.get();

        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user,product);
        if(existingCartItem !=null){
            //Update Quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem
                    .getQuantity())));
            cartItemRepository.save(existingCartItem);

        }else{

            //Create new Cart Item
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request
                    .getQuantity())));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean deleteFromCart(String userId, Long productId) {

        Optional<Product> optProduct =  productRepository.findById(productId);
        Optional<User>  userOpt = userRepository.findById(Long.valueOf(userId));

        if(optProduct.isPresent() && userOpt.isPresent()){
            cartItemRepository.deleteByUserAndProduct(userOpt.get()
                    ,optProduct.get());
            return true;
        }
         return false;
    }
}
