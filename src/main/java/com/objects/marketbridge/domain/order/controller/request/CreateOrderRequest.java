package com.objects.marketbridge.domain.order.controller.request;

import com.objects.marketbridge.domain.order.dto.CreateProdOrderDetailDto;
import com.objects.marketbridge.domain.order.dto.CreateProdOrderDto;
import com.objects.marketbridge.domain.order.dto.ProductInfoDto;
import com.objects.marketbridge.domain.order.exception.CustomLogicException;
import com.objects.marketbridge.domain.order.exception.ErrorCode;
import com.objects.marketbridge.global.error.OrderPriceMismatchException;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
public class CreateOrderRequest {

    @NotNull
    private String payMethod;

    @NotNull
    private String orderName;

    @NotNull
    private Long totalOrderPrice;

    @NotNull
    private Long addressId;

    private String successUrl;

    private String failUrl;


    private List<ProductInfoDto> productInfos = new ArrayList<>();

    @Builder(builderClassName = "requestBuilder", toBuilder = true)
    public CreateOrderRequest(String payMethod, String orderName, Long totalOrderPrice, Long addressId, String successUrl, String failUrl, @Singular("product") List<ProductInfoDto> productInfos) {
        validPaymentAmount(totalOrderPrice);
        validTotalOrderPrice(totalOrderPrice, productInfos);
        this.payMethod = payMethod;
        this.orderName = orderName;
        this.totalOrderPrice = totalOrderPrice;
        this.addressId = addressId;
        this.successUrl = successUrl;
        this.failUrl = failUrl;
        this.productInfos = productInfos;
    }

    private void validPaymentAmount(Long totalOrderPrice) {
        if (totalOrderPrice > 1000000000L || totalOrderPrice < 1000L) {
            throw new CustomLogicException("금액은 최대 1,000,000,000원 그리고 최저 1,000 원 만 거래 가능합니다", ErrorCode.INVALID_PAYMENT_AMOUNT);
        }
    }

    private void validTotalOrderPrice(Long totalOrderPrice, List<ProductInfoDto> productInfos) {
        Long sum = productInfos.stream()
                .mapToLong(ProductInfoDto::getUnitOrderPrice)
                .sum();

        if (!sum.equals(totalOrderPrice)) {
            throw new OrderPriceMismatchException("전달받은 totalOrderPrice : "+totalOrderPrice+", 계산한 totaOrderPrice : "+sum);
        }
    }

    public CreateProdOrderDto toProdOrderDto(Long memberId){
        return CreateProdOrderDto.builder()
                .memberId(memberId)
                .addressId(this.addressId)
                .totalOrderPrice(this.totalOrderPrice)
                .build();
    }

    public List<CreateProdOrderDetailDto> toProdOrderDetailDtos() {
        return productInfos.stream()
                .map(p -> CreateProdOrderDetailDto.builder()
                        .productId(p.getProductId())
                        .usedCouponId(p.getUsedCouponId())
                        .quantity(p.getQuantity())
                        .unitOrderPrice(p.getUnitOrderPrice()).build())
                .collect(Collectors.toList());
    }
}