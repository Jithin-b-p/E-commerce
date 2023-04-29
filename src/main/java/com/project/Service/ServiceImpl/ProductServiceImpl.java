package com.project.Service.ServiceImpl;

import com.project.Dto.RequestDto.ProductRequestDto;
import com.project.Dto.ResponseDto.ProductResponseDto;
import com.project.Entity.Item;
import com.project.Entity.Ordered;
import com.project.Entity.Product;
import com.project.Entity.Seller;
import com.project.Enum.ProductCategory;
import com.project.Enum.ProductStatus;
import com.project.Exception.QuantityException;
import com.project.Exception.SellerNotFoundException;
import com.project.Repository.ProductRepository;
import com.project.Repository.SellerRepository;
import com.project.Service.ProductService;
import com.project.Service.SellerService;
import com.project.Transformer.ProductTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Override
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws SellerNotFoundException {

        if(sellerRepository.findById(productRequestDto.getSellerId()).isEmpty()){
            throw new SellerNotFoundException("Seller Not Exist !!");
        }

        Seller seller = sellerRepository.findById(productRequestDto.getSellerId()).get();

        Product product = ProductTransformer.ProductRequestDtoToProduct(productRequestDto);
        product.setSeller(seller);

        //add product to current list of product in seller.
        seller.getProductList().add(product);

        //this will save both.
        sellerRepository.save(seller);

        ProductResponseDto productResponseDto = ProductTransformer.productToProductResponseDto(product);

        return productResponseDto;
    }

    @Override
    public List<ProductResponseDto> getAllProductsByCategory(ProductCategory category) {

        List<Product> products = productRepository.findAllByCategory(category);

        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for(Product product: products){

            productResponseDtoList.add(ProductTransformer.productToProductResponseDto(product));

        }
        return productResponseDtoList;
    }

    @Override
    public void decreaseProductQuantity(Item item) throws QuantityException {

        Product product = item.getProduct();
        int quantity = item.getRequiredQuantity();
        int currentQuantity = product.getQuantity();

        if(quantity > currentQuantity){
            throw new QuantityException("Required quantity exceed current available quantity !!");
        }
        product.setQuantity(currentQuantity - quantity);

        //when the quantity become zero.
        if(product.getQuantity() == 0){
            product.setProductStatus(ProductStatus.OUT_OF_STOCK);
        }

    }
}
