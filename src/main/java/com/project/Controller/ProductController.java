package com.project.Controller;

import com.project.Dto.RequestDto.ProductRequestDto;
import com.project.Dto.ResponseDto.ProductOutOfStockResponseDto;
import com.project.Dto.ResponseDto.ProductResponseDto;
import com.project.Enum.ProductCategory;
import com.project.Exception.ProductNotExistException;
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

    //get products by seller emailId API
    @GetMapping("/getProductsBySeller")
    public ResponseEntity getProductsOfSeller(@RequestParam("email") String email){

        try{

            List<ProductResponseDto> productResponseDtoList = productService.getProductsBySeller(email);
            return new ResponseEntity(productResponseDtoList, HttpStatus.OK);

        } catch (SellerNotFoundException e) {

            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    //delete product with productId API.
    @DeleteMapping("/deleteProduct")
    public ResponseEntity deleteProduct(@RequestParam("productId") int productId){

        try{

            String response = productService.deleteProduct(productId);
            return new ResponseEntity(response, HttpStatus.OK);

        } catch (ProductNotExistException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //Get top 5 cheap products API.
    @GetMapping("/getTop5Cheap")
    public ResponseEntity getTop5CheapProducts(){

        try{

            List<ProductResponseDto> productResponseDtoList = productService.getTop5CheapProducts();
            return new ResponseEntity<>(productResponseDtoList, HttpStatus.OK);

        } catch (ProductNotExistException e) {

            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //Get top 5 costliest products
    @GetMapping("/getTop5Costliest")
    public ResponseEntity getTop5CostliestProducts(){

        try{

            List<ProductResponseDto> productResponseDtoList = productService.getTop5CostliestProducts();
            return new ResponseEntity<>(productResponseDtoList,HttpStatus.ACCEPTED);

        } catch (ProductNotExistException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //find all out-of-stock products API
    @GetMapping("/findAllOutOfStock")
    public ResponseEntity getAllOutOfStockProducts(){

        try{
            List<ProductOutOfStockResponseDto> productOutOfStockResponseDtoList = productService.getAllOutOfStockProducts();
            return new ResponseEntity<>(productOutOfStockResponseDtoList, HttpStatus.OK);

        }catch(Exception e){

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }

    }

}
