package com.project.Transformer;

import com.project.Dto.RequestDto.ProductRequestDto;
import com.project.Dto.ResponseDto.ProductResponseDto;
import com.project.Entity.Product;
import com.project.Enum.ProductStatus;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductTransformer {

    public static Product ProductRequestDtoToProduct(ProductRequestDto productRequestDto){

        return Product.builder()
                .name(productRequestDto.getProductName())
                .quantity(productRequestDto.getQuantity())
                .price(productRequestDto.getPrice())
                .category(productRequestDto.getProductCategory())
                .productStatus(ProductStatus.AVAILABLE)
                .build();
    }

    public static ProductResponseDto productToProductResponseDto(Product product){

        return ProductResponseDto.builder()
                .productName(product.getName())
                .quantity(product.getQuantity())
                .productStatus(product.getProductStatus())
                .sellerName(product.getSeller().getName())
                .build();
    }

}
