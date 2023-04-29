package com.project.Dto.RequestDto;

import com.project.Enum.CardType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CardRequestDto {

    int cvv;

    String cardNo;

    CardType cardType;

    String email;
}
