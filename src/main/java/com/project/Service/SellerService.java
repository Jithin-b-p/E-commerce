package com.project.Service;

import com.project.Dto.RequestDto.SellerRequestDto;
import com.project.Dto.ResponseDto.GetAllSellersDto;
import com.project.Dto.ResponseDto.GetSellerResponseDto;
import com.project.Dto.ResponseDto.SellerResponseDto;
import com.project.Exception.EmailAlreadyPresentException;
import com.project.Exception.SellerNotFoundException;
import com.project.Repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface SellerService {

    public SellerResponseDto addSeller(SellerRequestDto sellerRequestDto) throws EmailAlreadyPresentException;

    public GetSellerResponseDto getSellerByEmail(String email) throws SellerNotFoundException;

    public GetAllSellersDto getAllSellers();

    public void deleteSellerByEmail(String email) throws SellerNotFoundException;
}
