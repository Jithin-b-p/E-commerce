package com.project.Service;

import com.project.Dto.RequestDto.ItemRequestDto;
import com.project.Entity.Item;
import com.project.Exception.InvalidCustomerException;
import com.project.Exception.QuantityException;
import com.project.Exception.ProductNotExistException;

public interface ItemService {

    public Item addItem(ItemRequestDto itemRequestDto) throws InvalidCustomerException, ProductNotExistException, QuantityException;
}
