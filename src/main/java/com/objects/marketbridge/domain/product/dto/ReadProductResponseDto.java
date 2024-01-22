package com.objects.marketbridge.domain.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ReadProductResponseDto {

    private Long productId;

    private Long categoryId;
    private Boolean isOwn; // 로켓 true , 오픈 마켓 false
    private String name;
    private Long price;
    private Boolean isSubs;
    private Long stock;
    private String thumbImg;

//    private List<String> itemImgUrls = new ArrayList<>();
//    private List<String> detailImgUrls = new ArrayList<>();

    private Long discountRate;
    private List<String> optionNames = new ArrayList<>();


//    public ReadProductResponseDto(Long productId) {
//        this.productId = productId;
//    }

    public ReadProductResponseDto
            (Long productId, Long categoryId, Boolean isOwn, String name, Long price,
             Boolean isSubs, Long stock, String thumbImg,
             Long discountRate) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.isOwn = isOwn;
        this.name = name;
        this.price = price;
        this.isSubs = isSubs;
        this.stock = stock;
        this.thumbImg = thumbImg;
        this.discountRate = discountRate;
    }
}