package com.project.Service.ServiceImpl;

import com.project.Entity.*;
import com.project.Exception.QuantityException;
import com.project.Service.OrderService;
import com.project.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    ProductService productService;

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
        order.setTotalOrderAmount(cart.getTotalAmount());

        return order;
    }

    @Override
    public String generateMaskedCard(String cardNo) {

        StringBuilder maskedCardNo = new StringBuilder();

        int length = cardNo.length();
        for(int i = 1; i <= length - 4; i++){
            maskedCardNo.append('*');
        }
        maskedCardNo.append(cardNo.substring(length - 4, length));

        return maskedCardNo.toString();
    }
}
