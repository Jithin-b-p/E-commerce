package com.project.Service.ServiceImpl;

import com.project.Dto.RequestDto.SellerRequestDto;
import com.project.Dto.ResponseDto.GetAllSellersDto;
import com.project.Dto.ResponseDto.GetSellerResponseDto;
import com.project.Dto.ResponseDto.SellerResponseDto;
import com.project.Entity.Seller;
import com.project.Exception.EmailAlreadyPresentException;
import com.project.Exception.SellerNotFoundException;
import com.project.Repository.SellerRepository;
import com.project.Service.SellerService;
import com.project.Transformer.SellerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public GetSellerResponseDto updateSellerMobNoByEmail(String email, String mobNo) throws SellerNotFoundException {

        Seller seller = sellerRepository.findByEmail(email);
        if(seller == null){

            throw new SellerNotFoundException("Invalid EmailID !!");

        }

        seller.setMobNo(mobNo);

        Seller savedSeller = sellerRepository.save(seller);

        return SellerTransformer.SellerToGetSellerResponseDto(savedSeller);

    }

    @Override
    public GetSellerResponseDto getSellerByEmail(String email) throws SellerNotFoundException {

        Seller seller = sellerRepository.findByEmail(email);
        if(seller == null){
            throw new SellerNotFoundException("Invalid EmailID !!");
        }

        return SellerTransformer.SellerToGetSellerResponseDto(seller);

    }

    @Override
    public GetAllSellersDto getAllSellers() {

        List<Seller> sellersList = sellerRepository.findAll();

        List<GetSellerResponseDto> getAllSellerResponseDtoList = new ArrayList<>();
        for(Seller seller: sellersList){

            getAllSellerResponseDtoList.add(SellerTransformer.SellerToGetSellerResponseDto(seller));

        }

        GetAllSellersDto getAllSellersDto = new GetAllSellersDto(getAllSellerResponseDtoList);

        return getAllSellersDto;
    }

    @Override
    public List<GetSellerResponseDto> findSellerBelowCertainAge(int age) throws Exception {

        List<Seller> sellerList = sellerRepository.findByAgeLessThan(age);

        if(sellerList.isEmpty()){

            throw new Exception("Sellers Of that age don't exist");

        }

        List<GetSellerResponseDto> getSellerResponseDtoList = new ArrayList<>();

        sellerList.forEach((seller) -> {

            getSellerResponseDtoList.add(SellerTransformer.SellerToGetSellerResponseDto(seller));

        });

        return getSellerResponseDtoList;
    }

    @Override
    public void deleteSellerByEmail(String email) throws SellerNotFoundException {

        Seller seller = sellerRepository.findByEmail(email);
        if(seller == null){
            throw new SellerNotFoundException("Invalid EmailID !!");
        }

        //deleting seller will also delete product and item (parent - child)
        sellerRepository.delete(seller);
    }




}
