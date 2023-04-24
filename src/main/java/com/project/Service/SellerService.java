package com.project.Service;

import com.project.Dto.RequestDto.SellerRequestDto;
import com.project.Dto.ResponseDto.SellerResponseDto;
import com.project.Exception.EmailAlreadyPresentException;
import com.project.Repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface SellerService {

    public SellerResponseDto addSeller(SellerRequestDto sellerRequestDto) throws EmailAlreadyPresentException;
}
