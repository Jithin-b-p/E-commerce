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

import java.util.List;

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

    //updating seller mobNo with email.
    @PutMapping("/updateSeller")
    public ResponseEntity updateSellerByEmail(@RequestParam("email") String email, @RequestParam("mobNo") String mobNo){

        try{
            GetSellerResponseDto getsellerResponseDto = sellerService.updateSellerMobNoByEmail(email, mobNo);
            return new ResponseEntity(getsellerResponseDto, HttpStatus.ACCEPTED);

        }catch(SellerNotFoundException e){

            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
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

    //finding sellers of less than particular age.
    @GetMapping("/getLessThanAge/{age}")
    public ResponseEntity getSellersBelowCertainAge(@PathVariable(name = "age") int age){

        try{
            List<GetSellerResponseDto> getSellerResponseDtoList = sellerService.findSellerBelowCertainAge(age);
            return new ResponseEntity(getSellerResponseDtoList, HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    //delete seller by email API
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
