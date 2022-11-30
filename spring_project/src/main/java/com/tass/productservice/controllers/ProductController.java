package com.tass.productservice.controllers;

import com.tass.productservice.model.BaseResponse;
import com.tass.productservice.model.request.ProductRequest;
import com.tass.productservice.services.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/product")
public class ProductController extends BaseController {
    @Autowired
    ProductService productService;

    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody  ProductRequest request){
        try {
            Optional<ProductRequest> optionalProductRequest = productService.findProductByBarcode(request.getBarcode());

            if (optionalProductRequest.isPresent()) {
                log.info("Lỗi trùng barcode.");
                return createdResponse(new BaseResponse(1000, ""));
            }
            if (request.getName().isEmpty() || request.getName() == null) {
                log.info("Lỗi thiếu tên sản phẩm.");
                return createdResponse(new BaseResponse(100, "Tên sản phẩm không được null"));
            }
            if (request.getImage().isEmpty() || request.getImage() == null) {
                log.info("Lỗi thiếu tên sản phẩm.");
                return createdResponse(new BaseResponse(101, "Ảnh sản phẩm không được null"));
            }
            if (request.getContent().isEmpty() || request.getContent() == null) {
                return createdResponse(new BaseResponse(101, "Content sản phẩm không được null"));
            }
            if (request.getDescription().isEmpty() || request.getDescription() == null) {
                return createdResponse(new BaseResponse(101, "Miêu tả sản phẩm không được null"));
            }
            productService.saveProduct(request);
            return  createdResponse(productService.createdProduct(request));
        }catch (Exception e){
            return  createdResponse(new BaseResponse(500,"Hệ thống đang quá tải xin vui lòng thử lại sau"),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
