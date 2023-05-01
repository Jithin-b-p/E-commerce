package com.project.Service.ServiceImpl;

import com.project.Dto.RequestDto.ProductRequestDto;
import com.project.Dto.ResponseDto.ProductOutOfStockResponseDto;
import com.project.Dto.ResponseDto.ProductResponseDto;
import com.project.Entity.Item;
import com.project.Entity.Product;
import com.project.Entity.Seller;
import com.project.Enum.ProductCategory;
import com.project.Enum.ProductStatus;
import com.project.Exception.ProductNotExistException;
import com.project.Exception.QuantityException;
import com.project.Exception.SellerNotFoundException;
import com.project.Repository.ProductRepository;
import com.project.Repository.SellerRepository;
import com.project.Service.ProductService;
import com.project.Transformer.ProductTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<ProductResponseDto> getProductsBySeller(String email) throws SellerNotFoundException {

        Seller seller = sellerRepository.findByEmail(email);
        if(seller == null){

            throw new SellerNotFoundException("Invalid EmailID");

        }

        List<Product> productList = seller.getProductList();

        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        productList.forEach((product) ->{
            productResponseDtoList.add(ProductTransformer.productToProductResponseDto(product));
        });

        return productResponseDtoList;

    }

    @Override
    public String deleteProduct(int productId) throws ProductNotExistException {

        Optional<Product> productOptional = productRepository.findById(productId);
        if(productOptional.isEmpty()){
            throw new ProductNotExistException("Invalid product ID");
        }
        Product product = productOptional.get();

        Seller seller = product.getSeller();
        seller.getProductList().remove(product);

        productRepository.delete(product);

        return "Product Deleted successfully..!!";
    }

    @Override
    public List<ProductResponseDto> getTop5CheapProducts() throws ProductNotExistException {

        List<Product> productList = productRepository.findTop5ByOrderByPriceAsc();
        if(productList.isEmpty()){
            throw new ProductNotExistException("No Products Exist");
        }
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        productList.forEach((product) ->{

            productResponseDtoList.add(ProductTransformer.productToProductResponseDto(product));
        });

        return productResponseDtoList;
    }

    @Override
    public List<ProductResponseDto> getTop5CostliestProducts() throws ProductNotExistException {

        List<Product> productList = productRepository.findTop5ByOrderByPriceDesc();
        if(productList.isEmpty()){
            throw new ProductNotExistException("No Products Exist");
        }

        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        productList.forEach((product) ->{

            productResponseDtoList.add(ProductTransformer.productToProductResponseDto(product));
        });

        return productResponseDtoList;


    }

    @Override
    public List<ProductOutOfStockResponseDto> getAllOutOfStockProducts() throws Exception {

        List<Product> productLists = productRepository.findAllOutOfStockProducts(String.valueOf(ProductStatus.OUT_OF_STOCK));

        if(productLists.isEmpty()){
            throw new Exception("All Products are available !!");
        }
        List<ProductOutOfStockResponseDto> productOutOfStockResponseDtoList = new ArrayList<>();

        productLists.forEach((product) ->{

            productOutOfStockResponseDtoList.add(ProductTransformer.productToProductOutOfStockResponseDto(product));

        });

        return productOutOfStockResponseDtoList;

    }
}
