package com.tass.productservice.services;


import com.tass.productservice.model.BaseResponse;
import com.tass.productservice.model.request.ProductRequest;
import com.tass.productservice.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    public BaseResponse createdProduct(ProductRequest request){

        return new BaseResponse();
    }
    public Optional<ProductRequest> findProductByBarcode(String barcode){
        return productRepository.findProductRequestByBarcode(barcode);
    }
    public ProductRequest saveProduct(ProductRequest productRequest){
        return productRepository.save(productRequest);
    }
}
