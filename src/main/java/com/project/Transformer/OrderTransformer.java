package com.project.Transformer;

import com.project.Dto.ResponseDto.OrderResponseDto;
import com.project.Entity.Ordered;
import jakarta.persistence.criteria.Order;

public class OrderTransformer {

    public static OrderResponseDto orderToOrderResponseDto(Ordered order){

        return OrderResponseDto.builder()
                .orderNo(order.getOrderNo())
                .orderDate(order.getOrderDate())
                .totalOrderAmount(order.getTotalOrderAmount())
                .cardUsed(order.getCardUsed())
                .customerName(order.getCustomer().getName())
                .build();
    }
}
