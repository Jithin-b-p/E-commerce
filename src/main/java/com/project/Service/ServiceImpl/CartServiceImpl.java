package com.project.Service.ServiceImpl;

import com.project.Dto.RequestDto.CheckOutCartRequestDto;
import com.project.Dto.ResponseDto.CartResponseDto;
import com.project.Dto.ResponseDto.ItemResponseDto;
import com.project.Dto.ResponseDto.OrderResponseDto;
import com.project.Entity.*;
import com.project.Exception.InvalidCardException;
import com.project.Exception.InvalidCustomerException;
import com.project.Repository.CardRepository;
import com.project.Repository.CartRepository;
import com.project.Repository.CustomerRepository;
import com.project.Repository.OrderedRepository;
import com.project.Service.CartService;
import com.project.Service.OrderService;
import com.project.Service.ProductService;
import com.project.Transformer.CartTransformer;
import com.project.Transformer.ItemTransformer;
import com.project.Transformer.OrderTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    OrderedRepository orderedRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @Override
    public CartResponseDto saveCart(int customerId, Item item) {

        Customer customer = customerRepository.findById(customerId).get();
        Cart cart = customer.getCart();

        int newTotal = cart.getTotalAmount() + (item.getProduct().getPrice() * item.getRequiredQuantity());
        cart.setTotalAmount(newTotal);

        cart.setNumberOfItems(cart.getItemList().size());

        cartRepository.save(cart);

        List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();
        for(Item itemInCart: cart.getItemList()){

            itemResponseDtoList.add(new ItemResponseDto(
                    itemInCart.getProduct().getName(),
                    itemInCart.getProduct().getPrice(),
                    itemInCart.getRequiredQuantity(),
                    (itemInCart.getProduct().getPrice()) * itemInCart.getRequiredQuantity())
            );
        }

        CartResponseDto cartResponseDto = CartTransformer.cartToCartResponseDto(cart);
        cartResponseDto.setLists(itemResponseDtoList);

        return cartResponseDto;

    }

    @Override
    public OrderResponseDto checkOutCart(CheckOutCartRequestDto checkOutCartRequestDto) throws Exception {

        Customer customer;
        try{

            customer = customerRepository.findById(checkOutCartRequestDto.getCustomerId()).get();

        }catch (Exception e){

            throw new InvalidCustomerException("Customer id is invalid");
        }

        Card card = cardRepository.findByCardNo(checkOutCartRequestDto.getCardNo());

        if(card == null || card.getCvv() != checkOutCartRequestDto.getCvv() || card.getCustomer() != customer){

            throw new InvalidCardException("Invalid card");

        }

        Cart cart = customer.getCart();
        if(cart.getNumberOfItems() == 0){

            throw new Exception("Cart is Empty");

        }

        try{

            Ordered order = orderService.placeOrder(customer, card);

            customer.getOrderList().add(order);
            //reset cart
            resetCart(cart);

            Ordered savedOrder = orderedRepository.save(order);

            OrderResponseDto orderResponseDto = OrderTransformer.orderToOrderResponseDto(savedOrder);

            List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();
            for(Item item: savedOrder.getItemList()){

                itemResponseDtoList.add(ItemTransformer.itemToItemResponseDto(item));

            }
            orderResponseDto.setItemResponseDtoList(itemResponseDtoList);

            return orderResponseDto;



        }catch (Exception e){
            throw new Exception(e.getMessage());
        }



    }

    public void resetCart(Cart cart){

        cart.setTotalAmount(0);
        cart.setNumberOfItems(0);

    }
}
