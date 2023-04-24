package com.project.Service.ServiceImpl;

import com.project.Dto.RequestDto.SellerRequestDto;
import com.project.Dto.ResponseDto.SellerResponseDto;
import com.project.Entity.Seller;
import com.project.Exception.EmailAlreadyPresentException;
import com.project.Repository.SellerRepository;
import com.project.Service.SellerService;
import com.project.Transformer.SellerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    SellerRepository sellerRepository;

    @Override
    public SellerResponseDto addSeller(SellerRequestDto sellerRequestDto) throws EmailAlreadyPresentException {

        if(sellerRepository.findByEmail(sellerRequestDto.getEmail()) != null){

            throw new EmailAlreadyPresentException("Seller Already Present !!");
        }
        //extracting data from request dto
        Seller seller = SellerTransformer.SellerRequestDtoToSeller(sellerRequestDto);

        sellerRepository.save(seller);

        //creating response dto
        SellerResponseDto sellerResponseDto = SellerTransformer.SellerToSellerResponseDto(seller);

        return sellerResponseDto;


    }
}
