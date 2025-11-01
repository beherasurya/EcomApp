package com.app.ecom.controller;

import com.app.ecom.dto.CartItermRequest;
import com.app.ecom.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItermRequest cartItermRequest){

        if(!cartService.addToCart(userId,cartItermRequest))
            return ResponseEntity.badRequest().body("Product Out of stock or user not found");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId
    ){
        boolean result = cartService.deleteFromCart(userId,productId);
        return result ? ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }
}
