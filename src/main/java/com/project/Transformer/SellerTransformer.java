package com.project.Transformer;

import com.project.Dto.RequestDto.SellerRequestDto;
import com.project.Dto.ResponseDto.GetSellerResponseDto;
import com.project.Dto.ResponseDto.SellerResponseDto;
import com.project.Entity.Seller;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SellerTransformer {

    public static Seller SellerRequestDtoToSeller(SellerRequestDto sellerRequestDto){

        return Seller.builder()
                .name(sellerRequestDto.getName())
                .age(sellerRequestDto.getAge())
                .email(sellerRequestDto.getEmail())
                .mobNo(sellerRequestDto.getMobNo())
                .build();
    }

    public static SellerResponseDto SellerToSellerResponseDto(Seller seller){

        return SellerResponseDto.builder()
                .name(seller.getName())
                .message("Seller added SuccessFully!!")
                .build();
    }

    public static GetSellerResponseDto SellerToGetSellerResponseDto(Seller seller){

        return GetSellerResponseDto.builder()
                .name(seller.getName())
                .age(seller.getAge())
                .mobNo(seller.getMobNo())
                .email(seller.getEmail())
                .build();
    }
}
