package com.project.Controller;

import com.project.Dto.RequestDto.CheckOutCartRequestDto;
import com.project.Dto.RequestDto.ItemRequestDto;
import com.project.Dto.ResponseDto.CartResponseDto;
import com.project.Dto.ResponseDto.OrderResponseDto;
import com.project.Entity.Item;
import com.project.Service.CartService;
import com.project.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    ItemService itemService;

    @Autowired
    CartService cartService;


    @PostMapping("/addToCart")
    public ResponseEntity addToCart(@RequestBody ItemRequestDto itemRequestDto){

        try{
            Item saveItem  = itemService.addItem(itemRequestDto);
            CartResponseDto cartResponseDto = cartService.saveCart(itemRequestDto.getCustomerId(), saveItem);

            return new ResponseEntity(cartResponseDto, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/checkout")
    public ResponseEntity checkOutCart(@RequestBody CheckOutCartRequestDto checkOutCartRequestDto){

        try {
            OrderResponseDto orderResponseDto = cartService.checkOutCart(checkOutCartRequestDto);
            return new ResponseEntity(orderResponseDto, HttpStatus.ACCEPTED);
        }catch (Exception e){

            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
