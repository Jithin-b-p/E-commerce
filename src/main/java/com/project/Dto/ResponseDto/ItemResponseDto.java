package com.project.Dto.ResponseDto;

import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ItemResponseDto {

     String productName;

     int price;

     int quantity;

     int totalPrice;
}
