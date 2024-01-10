package com.objects.marketbridge.domain.order.service;

import com.objects.marketbridge.domain.model.ProdOrder;
import com.objects.marketbridge.domain.model.StatusCodeType;
import com.objects.marketbridge.domain.order.service.port.OrderDetailRepository;
import com.objects.marketbridge.domain.order.service.port.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderCancelService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public void orderCancel(Long orderId, String reason) {

        // orderId로 주문을 조회한다.
        Optional<ProdOrder> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            throw new IllegalArgumentException("조회된 주문이 없습니다.");
        }
        ProdOrder order = orderOptional.get();

        // 만약 배송 중 이라면 주문 취소를 할 수 없다.
        // 여차하면 객체로 빼야함
        // TODO 주문 취소할 수 없는 상황이 언제인지 정책 정해야 함!
        if (order.getStatusCode() == StatusCodeType.DELIVERY_ING.getCode()) {
            throw new IllegalStateException("주문 취소할 수 없는 상태입니다.");
        }

        orderDetailRepository.changeAllType(orderId, StatusCodeType.ORDER_CANCEL.getCode());
        orderDetailRepository.addReason(orderId, reason);

        // 재고의 수량을 늘리자 (동시성 문제)
        // 고려사항 : 쿠팡이라면 orderDetail에 해당하는 상품들이 어디 wherehouse의 상품인지 알고있어야 한다.
        // 1. orderDetails에 해당하는 상품들을 리스트로 만들자.
        // 2. 상품 리스트들을 조건으로 상품 stock을 가지고 오자.
        // 3. stock의 재고량을 orderDetaeil의 quantity만큼 증가시키자.


        // ProdOrder에 존재하는 totalPrice의 값을 주문 유저의 계좌로 환불해주자.
            // (재고 수량 동시성 예외가 터져도 환불은 해줌 -> 재고 문제는 따로 처리해야 함)
    }
}