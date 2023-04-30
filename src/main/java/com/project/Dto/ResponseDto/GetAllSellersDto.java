package com.project.Dto.ResponseDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class GetAllSellersDto {

    List<GetSellerResponseDto> listOfSellers;

}
