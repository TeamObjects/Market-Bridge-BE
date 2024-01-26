package com.objects.marketbridge.domain.order.entity;

import com.objects.marketbridge.model.BaseEntity;
import com.objects.marketbridge.model.Coupon;
import com.objects.marketbridge.model.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.objects.marketbridge.domain.order.entity.StatusCodeType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    private Long quantity;

    private String orderNo;

    private Long price;

    private String statusCode;

    private LocalDateTime deliveredDate;

    private String reason;

    private String tid;

    private LocalDateTime cancelledAt;

    @Builder
    public OrderDetail(Order order, String orderNo, String tid, Product product, Coupon coupon,  Long quantity, Long price, String statusCode, LocalDateTime deliveredDate, String reason, LocalDateTime cancelledAt) {
        this.orderNo = orderNo;
        this.tid = tid;
        this.order = order;
        this.product = product;
        this.coupon = coupon;
        this.quantity = quantity;
        this.price = price;
        this.statusCode = statusCode;
        this.deliveredDate = deliveredDate;
        this.reason = reason;
        this.cancelledAt = cancelledAt;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void changeStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public static OrderDetail create(Order order, Product product, String orderNo, Coupon coupon, Long quantity, Long price, String statusCode) {

        return OrderDetail.builder()
                .order(order)
                .orderNo(orderNo)
                .product(product)
                .coupon(coupon)
                .quantity(quantity)
                .price(price)
                .statusCode(statusCode)
                .build();
    }

    public void cancel(String reason, String statusCode) {
        if (Objects.equals(statusCode, DELIVERY_COMPLETED.getCode())) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.statusCode = statusCode;
        this.reason = reason;
        this.product.increase(quantity);
    }

    public void returnCoupon() {
        coupon.returnCoupon();
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}