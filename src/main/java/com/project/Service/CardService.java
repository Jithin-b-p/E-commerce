package com.project.Service;

import com.project.Dto.RequestDto.CardRequestDto;
import com.project.Dto.ResponseDto.CardResponseDto;

public interface CardService {

    CardResponseDto addCard(CardRequestDto cardRequestDto) throws Exception;
}
