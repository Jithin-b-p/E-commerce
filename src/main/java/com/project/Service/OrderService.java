package com.project.Service;

import com.project.Entity.Card;
import com.project.Entity.Customer;
import com.project.Entity.Ordered;

public interface OrderService {

    public Ordered placeOrder(Customer customer, Card card) throws Exception;

    public String generateMaskedCard(String cardNo);
}
