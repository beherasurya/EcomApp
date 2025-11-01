package com.app.ecom.dto;

import lombok.Data;

@Data
public class CartItermRequest {

    private Long productId;
    private Integer quantity;
}
