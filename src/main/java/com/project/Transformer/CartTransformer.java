package com.project.Transformer;

import com.project.Dto.ResponseDto.CartResponseDto;
import com.project.Entity.Cart;

public class CartTransformer {

    public static CartResponseDto cartToCartResponseDto(Cart cart){

        return CartResponseDto.builder()
                .customerName(cart.getCustomer().getName())
                .totalAmount(cart.getTotalAmount())
                .numberOfItems(cart.getNumberOfItems())
                .build();
    }
}
