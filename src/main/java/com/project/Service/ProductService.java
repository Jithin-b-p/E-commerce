package com.project.Service;

import com.project.Dto.RequestDto.ProductRequestDto;
import com.project.Dto.ResponseDto.ProductOutOfStockResponseDto;
import com.project.Dto.ResponseDto.ProductResponseDto;
import com.project.Entity.Item;
import com.project.Enum.ProductCategory;
import com.project.Exception.ProductNotExistException;
import com.project.Exception.QuantityException;
import com.project.Exception.SellerNotFoundException;

import java.util.List;

public interface ProductService {

    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws SellerNotFoundException;

    public List<ProductResponseDto> getAllProductsByCategory(ProductCategory category);

    public void decreaseProductQuantity(Item item) throws QuantityException;

    public List<ProductResponseDto> getProductsBySeller(String email) throws SellerNotFoundException;

    public String deleteProduct(int productId) throws ProductNotExistException;

    public List<ProductResponseDto> getTop5CheapProducts() throws ProductNotExistException;

    public List<ProductResponseDto> getTop5CostliestProducts() throws ProductNotExistException;

    public List<ProductOutOfStockResponseDto> getAllOutOfStockProducts() throws Exception;
}
