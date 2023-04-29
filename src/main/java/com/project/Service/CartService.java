package com.project.Service;

import com.project.Dto.RequestDto.CheckOutCartRequestDto;
import com.project.Dto.ResponseDto.CartResponseDto;
import com.project.Dto.ResponseDto.OrderResponseDto;
import com.project.Entity.Item;
import com.project.Exception.InvalidCardException;
import com.project.Exception.InvalidCustomerException;

public interface CartService {

    public CartResponseDto saveCart(int customerId, Item item);

    public OrderResponseDto checkOutCart(CheckOutCartRequestDto checkOutCartRequestDto) throws Exception;
}
