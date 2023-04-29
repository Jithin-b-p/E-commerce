package com.project.Transformer;

import com.project.Dto.RequestDto.ItemRequestDto;
import com.project.Dto.ResponseDto.ItemResponseDto;
import com.project.Entity.Item;

public class ItemTransformer {


    public static Item itemRequestDtoToItem(ItemRequestDto itemRequestDto){

        return Item.builder()
                .requiredQuantity(itemRequestDto.getRequiredQuantity())
                .build();

    }

    public static ItemResponseDto itemToItemResponseDto(Item item){

        return ItemResponseDto.builder()
                .productName(item.getProduct().getName())
                .price(item.getProduct().getPrice())
                .quantity(item.getRequiredQuantity())
                .totalPrice((item.getProduct().getPrice()*item.getRequiredQuantity()))
                .build();
    }
}
