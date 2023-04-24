package com.project.Dto.RequestDto;

import com.project.Enum.ProductCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProductRequestDto {

    String productName;

    int price;

    int quantity;

    ProductCategory productCategory;

    int sellerId;


}
