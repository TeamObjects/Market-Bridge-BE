package com.objects.marketbridge.domain.model;

import com.objects.marketbridge.domain.order.entity.ProdOrderDetail;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<ProdOrderDetail> prodOrderDetails = new ArrayList<>();

//    @OneToMany(mappedBy = "product")
//    private List<ProdOption> prodOptions = new ArrayList<>();

//    TODO 넣을지 말지
//    private String productNo;

    private Boolean isOwn; // 로켓 true , 오픈 마켓 false

    private String name;

    private Long price;

    private Boolean isSubs;

    private Long stock;

    private String thumbImg;

    private Long discountRate;


    @Builder
    private Product(Category category, List<ProdOrderDetail> prodOrderDetails, Boolean isOwn, String name, Long price, Boolean isSubs, Long stock, String thumbImg, Long discountRate) {
        this.category = category;
        this.prodOrderDetails = prodOrderDetails;
        this.isOwn = isOwn; // 로켓 true , 오픈 마켓 false
        this.name = name;
        this.price = price;
        this.isSubs = isSubs;
        this.stock = stock;
        this.thumbImg = thumbImg;
        this.discountRate = discountRate;
    }

    public void addProdOrderDetail(ProdOrderDetail prodOrderDetail) {
        prodOrderDetails.add(prodOrderDetail);
        prodOrderDetail.setProduct(this);
    }

    public void increase(Long quantity) {
        stock += quantity;
    }

    public void updateProduct(Category category, Boolean isOwn, String name, Long price, Boolean isSubs, Long stock, String thumbImg, Long discountRate) {
        this.category = category;
        this.isOwn = isOwn; // 로켓 true , 오픈 마켓 false
        this.name = name;
        this.price = price;
        this.isSubs = isSubs;
        this.stock = stock;
        this.thumbImg = thumbImg;
        this.discountRate = discountRate;
    }
}
