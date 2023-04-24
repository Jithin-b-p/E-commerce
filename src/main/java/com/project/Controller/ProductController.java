package com.project.Controller;

import com.project.Dto.RequestDto.ProductRequestDto;
import com.project.Dto.ResponseDto.ProductResponseDto;
import com.project.Enum.ProductCategory;
import com.project.Exception.SellerNotFoundException;
import com.project.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody ProductRequestDto productRequestDto){

        try{
            ProductResponseDto productResponseDto = productService.addProduct(productRequestDto);

            return new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);
        }catch(Exception e){

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    //get all products of particular category
    @GetMapping("/get/{category}")
    public ResponseEntity getAllProductByCategory(@PathVariable("category")ProductCategory category){

        List<ProductResponseDto> productResponseDtoList = productService.getAllProductsByCategory(category);
        if(productResponseDtoList.isEmpty()){
            return new ResponseEntity<>("Products not exist for this category", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(productResponseDtoList, HttpStatus.OK);
    }
}
