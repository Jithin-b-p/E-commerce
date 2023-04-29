package com.project.Service.ServiceImpl;

import com.project.Dto.RequestDto.CustomerRequestDto;
import com.project.Dto.ResponseDto.CartResponseDto;
import com.project.Dto.ResponseDto.CustomerResponseDto;
import com.project.Entity.Cart;
import com.project.Entity.Customer;
import com.project.Exception.EmailAlreadyPresentException;
import com.project.Repository.CartRepository;
import com.project.Repository.CustomerRepository;
import com.project.Service.CustomerService;
import com.project.Transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CartRepository cartRepository;

    @Override
    public CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto) throws EmailAlreadyPresentException {

        if(customerRepository.findByEmail(customerRequestDto.getEmail()).isPresent()){

            throw new EmailAlreadyPresentException("Customer Already present");
        }
        Customer customer = CustomerTransformer.customerRequestDtoToCustomer(customerRequestDto);

        Cart cart = Cart.builder()
                .totalAmount(0)
                .numberOfItems(0)
                .customer(customer)
                .build();

        customer.setCart(cart);

//save customer and cart
        customerRepository.save(customer);

        CustomerResponseDto customerResponseDto = CustomerTransformer.CustomerToCustomerResponseDto(customer);
        return customerResponseDto;

    }
}
