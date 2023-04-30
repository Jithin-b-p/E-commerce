package com.project.Controller;

import com.project.Dto.RequestDto.ItemRequestDto;
import com.project.Dto.RequestDto.OrderRequestDto;
import com.project.Dto.ResponseDto.OrderResponseDto;
import com.project.Exception.InvalidCardException;
import com.project.Exception.InvalidCustomerException;
import com.project.Exception.ProductNotExistException;
import com.project.Exception.QuantityException;
import com.project.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    //API to order items individually
    @PostMapping("/place")
    public ResponseEntity placeDirectOrder(@RequestBody OrderRequestDto orderRequestDto) throws InvalidCustomerException, ProductNotExistException, QuantityException, InvalidCardException {

        OrderResponseDto orderResponseDto = orderService.placeOrder(orderRequestDto);
        return new ResponseEntity(orderResponseDto, HttpStatus.ACCEPTED);
    }
}
