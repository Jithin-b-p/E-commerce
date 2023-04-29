package com.project.Service;

import com.project.Dto.RequestDto.ProductRequestDto;
import com.project.Dto.ResponseDto.ProductResponseDto;
import com.project.Entity.Item;
import com.project.Enum.ProductCategory;
import com.project.Exception.QuantityException;
import com.project.Exception.SellerNotFoundException;

import java.util.List;

public interface ProductService {

    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws SellerNotFoundException;

    public List<ProductResponseDto> getAllProductsByCategory(ProductCategory category);

    public void decreaseProductQuantity(Item item) throws QuantityException;
}
