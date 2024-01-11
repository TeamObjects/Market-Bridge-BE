package com.objects.marketbridge.domain.model;

import com.objects.marketbridge.domain.order.domain.ProdOrderDetail;
import com.objects.marketbridge.domain.order.domain.StatusCodeType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProdOrderDetailTest {

    @Test
    @DisplayName("주문 상세의 코드를 바꾼다.")
    public void changeStatusCode() {
        // given
        String givenStatusCode = StatusCodeType.ORDER_RECEIVED.getCode();
        ProdOrderDetail orderDetail = createOrderDetail();

        // when
        orderDetail.changeStatusCode(givenStatusCode);

        // then
        Assertions.assertThat(orderDetail.getStatusCode()).isEqualTo(givenStatusCode);
    }

    private ProdOrderDetail createOrderDetail() {
        return ProdOrderDetail
                .builder()
                .statusCode(StatusCodeType.PAYMENT_COMPLETED.getCode())
                .build();
    }

}