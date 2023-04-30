package com.project.Service;

import com.project.Dto.RequestDto.OrderRequestDto;
import com.project.Dto.ResponseDto.OrderResponseDto;
import com.project.Entity.Card;
import com.project.Entity.Customer;
import com.project.Entity.Ordered;
import com.project.Exception.InvalidCardException;
import com.project.Exception.InvalidCustomerException;
import com.project.Exception.ProductNotExistException;
import com.project.Exception.QuantityException;

public interface OrderService {

    public Ordered placeOrder(Customer customer, Card card) throws Exception;

    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto)throws InvalidCustomerException, InvalidCardException, QuantityException, ProductNotExistException;

    public String generateMaskedCard(String cardNo);
}
