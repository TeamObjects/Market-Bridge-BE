package com.objects.marketbridge.payment.service;

import com.objects.marketbridge.order.domain.Order;
import com.objects.marketbridge.order.domain.OrderDetail;
import com.objects.marketbridge.order.domain.StatusCodeType;
import com.objects.marketbridge.order.service.port.OrderCommendRepository;
import com.objects.marketbridge.common.dto.KakaoPayApproveResponse;
import com.objects.marketbridge.order.service.port.OrderQueryRepository;
import com.objects.marketbridge.payment.domain.Amount;
import com.objects.marketbridge.payment.domain.CardInfo;
import com.objects.marketbridge.payment.domain.Payment;
import com.objects.marketbridge.payment.service.port.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderCommendRepository orderCommendRepository;
    private final OrderQueryRepository orderQueryRepository;

    @Transactional
    public void create(KakaoPayApproveResponse response) {

        // 1. Payment 엔티티 생성
        Payment payment = createPayment(response);

        // 2. Order - Payment 연관관계 매핑
        Order order = orderQueryRepository.findByOrderNo(response.getPartnerOrderId());
        payment.linkOrder(order);

        // 3. orderDetail 의 statusCode 업데이트
        List<OrderDetail> orderDetails = order.getOrderDetails();
        orderDetails.forEach(o -> o.changeStatusCode(StatusCodeType.PAYMENT_COMPLETED.getCode()));

        // 4. 영속성 저장
        paymentRepository.save(payment);

        //TODO : 5. 판매자 계좌 변경
    }

    private Payment createPayment(KakaoPayApproveResponse response) {

        String orderNo = response.getPartnerOrderId();
        String paymentMethod = response.getPaymentMethodType();
        String tid = response.getTid();
        CardInfo cardInfo = response.getCardInfo();
        Amount amount = response.getAmount();

        return Payment.create(orderNo, paymentMethod, tid, cardInfo, amount);
    }
}