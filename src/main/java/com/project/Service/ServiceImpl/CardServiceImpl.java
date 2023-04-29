package com.project.Service.ServiceImpl;

import com.project.Dto.RequestDto.CardRequestDto;
import com.project.Dto.ResponseDto.CardResponseDto;
import com.project.Entity.Card;
import com.project.Entity.Customer;
import com.project.Repository.CardRepository;
import com.project.Repository.CustomerRepository;
import com.project.Service.CardService;
import com.project.Transformer.CardTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public CardResponseDto addCard(CardRequestDto cardRequestDto) throws Exception {

        if(customerRepository.findByEmail(cardRequestDto.getEmail()).isEmpty()){

            throw new Exception("Customer Not exist");

        }

        Card card = CardTransformer.cardRequestDtoToCard(cardRequestDto);
        Customer customer = customerRepository.findByEmail(cardRequestDto.getEmail()).get();
        card.setCustomer(customer);
        customer.getCardList().add(card);
        customerRepository.save(customer);

        return CardTransformer.cardToCardResponseDto(card);

    }
}
