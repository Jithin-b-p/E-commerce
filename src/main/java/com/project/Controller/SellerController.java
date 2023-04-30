package com.project.Controller;

import com.project.Dto.RequestDto.SellerRequestDto;
import com.project.Dto.ResponseDto.GetAllSellersDto;
import com.project.Dto.ResponseDto.GetSellerResponseDto;
import com.project.Dto.ResponseDto.SellerResponseDto;
import com.project.Exception.EmailAlreadyPresentException;
import com.project.Exception.SellerNotFoundException;
import com.project.Service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

    //add seller API
    @PostMapping("/add")
    public ResponseEntity addSeller(@RequestBody SellerRequestDto sellerRequestDto) throws EmailAlreadyPresentException {

        try{
            SellerResponseDto sellerResponseDto = sellerService.addSeller(sellerRequestDto);
            return new ResponseEntity<>(sellerResponseDto, HttpStatus.CREATED);
        }catch (Exception e){

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //Get seller by email API
    @GetMapping("/getByMail")
    public ResponseEntity getByEmail(@RequestParam("email") String email){

        try {

            GetSellerResponseDto getSellerResponseDto = sellerService.getSellerByEmail(email);
            return new ResponseEntity(getSellerResponseDto, HttpStatus.OK);

        }catch(SellerNotFoundException e){

            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    //get all seller API
    @GetMapping("/getAll")
    public ResponseEntity getAllSellers(){

        GetAllSellersDto getAllSellersDto = sellerService.getAllSellers();
        return new ResponseEntity(getAllSellersDto, HttpStatus.OK);

    }

    //delete seller by email
    @DeleteMapping("/delete/{email}")
    public ResponseEntity deleteSellerByEmail(@PathVariable("email") String email){

        try{

            sellerService.deleteSellerByEmail(email);
            return new ResponseEntity<>("Seller deleted Successfully !!", HttpStatus.OK);

        }catch (SellerNotFoundException e){

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }
}
