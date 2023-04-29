package com.project.Service.ServiceImpl;

import com.project.Dto.RequestDto.ItemRequestDto;
import com.project.Entity.Customer;
import com.project.Entity.Item;
import com.project.Entity.Product;
import com.project.Enum.ProductStatus;
import com.project.Exception.InvalidCustomerException;
import com.project.Exception.QuantityException;
import com.project.Exception.ProductNotExistException;
import com.project.Repository.CustomerRepository;
import com.project.Repository.ItemRepository;
import com.project.Repository.ProductRepository;
import com.project.Service.ItemService;
import com.project.Transformer.ItemTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public Item addItem(ItemRequestDto itemRequestDto) throws InvalidCustomerException, ProductNotExistException, QuantityException {

        Customer customer;
        try{

            customer = customerRepository.findById(itemRequestDto.getCustomerId()).get();

        }catch(Exception e){

            throw new InvalidCustomerException("Invalid customer Id");

        }

        Product product;
        try{

            product = productRepository.findById(itemRequestDto.getProductId()).get();

        }catch (Exception e){

            throw new ProductNotExistException("Product doesn't exist");
        }

        if(itemRequestDto.getRequiredQuantity() > product.getQuantity()){

            throw new QuantityException("Quantity is less !!");
        }

        if(product.getProductStatus() != ProductStatus.AVAILABLE){

            throw new QuantityException("Product Out of Stock !!");

        }

        Item item = ItemTransformer.itemRequestDtoToItem(itemRequestDto);
        item.setCart(customer.getCart());
        item.setProduct(product);

        product.getItemList().add(item);

        //this will save product and item.
        Product saveProduct = productRepository.save(product);

        return item;


    }

}
