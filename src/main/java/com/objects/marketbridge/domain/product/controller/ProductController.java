package com.objects.marketbridge.domain.product.controller;

import com.objects.marketbridge.domain.model.Category;
import com.objects.marketbridge.domain.model.Product;
import com.objects.marketbridge.domain.product.dto.ProductDto;
import com.objects.marketbridge.domain.product.dto.ProductRequestDto;
import com.objects.marketbridge.domain.product.dto.ProductResponseDto;
import com.objects.marketbridge.domain.product.service.ProductService;
import com.objects.marketbridge.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public ApiResponse<ProductResponseDto> registerProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {

//        ProductResponseDto productResponseDto = new ProductResponseDto(
//                productRequestDto.getCategoryId(),
//                productRequestDto.getIsOwn(),
//                productRequestDto.getName(),
//                productRequestDto.getPrice(),
//                productRequestDto.getIsSubs(),
//                productRequestDto.getStock(),
//                productRequestDto.getThumbImg(),
//                productRequestDto.getDiscountRate());

        ProductResponseDto productResponseDto = new ProductResponseDto();

        productService.registerProduct(productRequestDto);

        return ApiResponse.ok(productResponseDto);
    }

    @GetMapping("/products")
    public ProductResponseDto getProductList(@Valid @RequestBody ProductRequestDto request){
        // 담을거 가져오기
        // 현재 임시로 기본생성자 사용.
        return new ProductResponseDto();
    }
}
