package com.project.Service;

import com.project.Dto.RequestDto.ProductRequestDto;
import com.project.Dto.ResponseDto.ProductResponseDto;
import com.project.Enum.ProductCategory;
import com.project.Exception.SellerNotFoundException;

import java.util.List;

public interface ProductService {

    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws SellerNotFoundException;

    public List<ProductResponseDto> getAllProductsByCategory(ProductCategory category);
}
