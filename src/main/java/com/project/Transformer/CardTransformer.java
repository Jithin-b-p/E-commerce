package com.project.Transformer;


import com.project.Dto.RequestDto.CardRequestDto;
import com.project.Dto.ResponseDto.CardResponseDto;
import com.project.Entity.Card;

public class CardTransformer {

    public static Card cardRequestDtoToCard(CardRequestDto cardRequestDto){

        return Card.builder()
                .cardNo(cardRequestDto.getCardNo())
                .cardType(cardRequestDto.getCardType())
                .cvv(cardRequestDto.getCvv())
                .expiryDate(cardRequestDto.getExpireDate())
                .build();
    }

    public static CardResponseDto cardToCardResponseDto(Card card){

        return CardResponseDto.builder()
                .cardNo(card.getCardNo())
                .customerName(card.getCustomer().getName())
                .build();
    }
}
