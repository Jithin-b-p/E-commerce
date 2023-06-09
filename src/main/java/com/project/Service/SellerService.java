package com.project.Service;

import com.project.Dto.RequestDto.SellerRequestDto;
import com.project.Dto.ResponseDto.GetAllSellersDto;
import com.project.Dto.ResponseDto.GetSellerResponseDto;
import com.project.Dto.ResponseDto.SellerResponseDto;
import com.project.Exception.EmailAlreadyPresentException;
import com.project.Exception.SellerNotFoundException;
import com.project.Repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface SellerService {

    SellerResponseDto addSeller(SellerRequestDto sellerRequestDto) throws EmailAlreadyPresentException;

    GetSellerResponseDto updateSellerMobNoByEmail(String email, String mobNo) throws SellerNotFoundException;

    GetSellerResponseDto getSellerByEmail(String email) throws SellerNotFoundException;

    GetAllSellersDto getAllSellers();

    List<GetSellerResponseDto> findSellerBelowCertainAge(int age) throws Exception;

    void deleteSellerByEmail(String email) throws SellerNotFoundException;
}

