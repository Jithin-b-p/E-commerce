package com.project.Transformer;

import com.project.Dto.RequestDto.CustomerRequestDto;
import com.project.Dto.ResponseDto.CustomerResponseDto;
import com.project.Entity.Card;
import com.project.Entity.Customer;

public class CustomerTransformer {

    public static Customer customerRequestDtoToCustomer(CustomerRequestDto customerRequestDto){

        return Customer.builder()
                .age(customerRequestDto.getAge())
                .mobNo(customerRequestDto.getMobNo())
                .email(customerRequestDto.getEmail())
                .name(customerRequestDto.getName())
                .address(customerRequestDto.getAddress())
                .build();


    }

    public static CustomerResponseDto CustomerToCustomerResponseDto(Customer customer){


        return CustomerResponseDto.builder()
                .name(customer.getName())
                .message("Welcome to amazon !!")
                .build();
    }


}
