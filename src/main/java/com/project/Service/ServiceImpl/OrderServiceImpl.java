package com.project.Service.ServiceImpl;

import com.project.Dto.RequestDto.ItemRequestDto;
import com.project.Dto.RequestDto.OrderRequestDto;
import com.project.Dto.ResponseDto.ItemResponseDto;
import com.project.Dto.ResponseDto.OrderResponseDto;
import com.project.Entity.*;
import com.project.Enum.ProductStatus;
import com.project.Exception.InvalidCardException;
import com.project.Exception.InvalidCustomerException;
import com.project.Exception.ProductNotExistException;
import com.project.Exception.QuantityException;
import com.project.Repository.CardRepository;
import com.project.Repository.CustomerRepository;
import com.project.Repository.OrderedRepository;
import com.project.Repository.ProductRepository;
import com.project.Service.OrderService;
import com.project.Service.ProductService;
import com.project.Transformer.ItemTransformer;
import com.project.Transformer.OrderTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    ProductService productService;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderedRepository orderedRepository;

    @Override
    public Ordered placeOrder(Customer customer, Card card) throws Exception {

        Cart cart = customer.getCart();

        Ordered order = new Ordered();
        order.setOrderNo(String.valueOf(UUID.randomUUID()));

        String cardNo = card.getCardNo();

        String maskedCardNo = generateMaskedCard(cardNo);

        order.setCardUsed(maskedCardNo);
        order.setCustomer(customer);

        List<Item> orderedItems = new ArrayList<>();
        for(Item item: cart.getItemList()){

            try{
                productService.decreaseProductQuantity(item);
                orderedItems.add(item);

            } catch (QuantityException e) {

                throw new QuantityException(e.getMessage());
            }


        }
        order.setItemList(orderedItems);
        for(Item item: orderedItems){
            item.setOrdered(order);
        }
        order.setTotalOrderAmount(cart.getTotalAmount());

        return order;
    }

    @Override
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) throws InvalidCustomerException, InvalidCardException, QuantityException, ProductNotExistException {

        Customer customer;
        try{

            customer = customerRepository.findById(orderRequestDto.getCustomerId()).get();

        }catch (Exception e){

            throw new InvalidCustomerException("Customer id is invalid");
        }

        Card card = cardRepository.findByCardNo(orderRequestDto.getCardNo());

        if(card == null || card.getCvv() != orderRequestDto.getCvv() || card.getCustomer() != customer){

            throw new InvalidCardException("Invalid card");

        }

        Product product;
        try{

            product = productRepository.findById(orderRequestDto.getProductId()).get();

        }catch (Exception e){

            throw new ProductNotExistException("Product doesn't exist");
        }

        if(orderRequestDto.getRequiredQuantity() > product.getQuantity()){

            throw new QuantityException("Quantity is less !!");
        }

        if(product.getProductStatus() != ProductStatus.AVAILABLE){

            throw new QuantityException("Product Out of Stock !!");

        }

        ItemRequestDto itemRequestDto = new ItemRequestDto(orderRequestDto.getCustomerId(),
                orderRequestDto.getProductId(), orderRequestDto.getRequiredQuantity());
        Item item = ItemTransformer.itemRequestDtoToItem(itemRequestDto);
        item.setProduct(product);

        //create order
        Ordered order = new Ordered();
        order.setOrderNo(String.valueOf(UUID.randomUUID()));

        String cardNo = card.getCardNo();

        String maskedCardNo = generateMaskedCard(cardNo);

        order.setCardUsed(maskedCardNo);
        order.setCustomer(customer);
        try{
            productService.decreaseProductQuantity(item);
            order.getItemList().add(item);

        } catch (QuantityException e) {

            throw new QuantityException(e.getMessage());
        }

        item.setProduct(product);

        product.getItemList().add(item);


        item.setOrdered(order);
        customer.getOrderList().add(order);
        order.setTotalOrderAmount(item.getRequiredQuantity() * product.getPrice());
        orderedRepository.save(order);


        OrderResponseDto orderResponseDto = OrderTransformer.orderToOrderResponseDto(order);

        List<ItemResponseDto> orderItem = new ArrayList<>();
        orderItem.add(ItemTransformer.itemToItemResponseDto(item));
        orderResponseDto.setItemResponseDtoList(orderItem);

        return orderResponseDto;
    }

    @Override
    public String generateMaskedCard(String cardNo) {

        StringBuilder maskedCardNo = new StringBuilder();

        int length = cardNo.length();
        for(int i = 1; i <= length - 4; i++){
            maskedCardNo.append('X');
        }
        maskedCardNo.append(cardNo.substring(length - 4, length));

        return maskedCardNo.toString();
    }
}
