package com.project.Dto.ResponseDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class OrderResponseDto {

    String customerName;

    String orderNo;

    int totalOrderAmount;

    LocalDateTime orderDate;

    String cardUsed;

    List<ItemResponseDto> itemResponseDtoList;


}
