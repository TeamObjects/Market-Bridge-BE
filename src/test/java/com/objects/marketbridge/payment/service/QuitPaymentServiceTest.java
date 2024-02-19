package com.objects.marketbridge.payment.service;

import com.objects.marketbridge.coupon.domain.Coupon;
import com.objects.marketbridge.coupon.domain.MemberCoupon;
import com.objects.marketbridge.member.domain.*;
import com.objects.marketbridge.member.service.port.MemberRepository;
import com.objects.marketbridge.order.domain.Order;
import com.objects.marketbridge.order.domain.OrderDetail;
import com.objects.marketbridge.order.service.port.OrderCommendRepository;
import com.objects.marketbridge.order.service.port.OrderDetailQueryRepository;
import com.objects.marketbridge.order.service.port.OrderDtoRepository;
import com.objects.marketbridge.order.service.port.OrderQueryRepository;
import com.objects.marketbridge.payment.domain.Payment;
import com.objects.marketbridge.payment.service.port.PaymentRepository;
import com.objects.marketbridge.product.domain.Product;
import com.objects.marketbridge.coupon.service.port.CouponRepository;
import com.objects.marketbridge.product.infra.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.objects.marketbridge.order.domain.StatusCodeType.PAYMENT_COMPLETED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Slf4j
class QuitPaymentServiceTest {

    @Autowired OrderCommendRepository orderCommendRepository;
    @Autowired OrderDtoRepository orderDtoRepository;
    @Autowired OrderQueryRepository orderQueryRepository;
    @Autowired OrderDetailQueryRepository orderDetailQueryRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired ProductRepository productRepository;
    @Autowired QuitPaymentService quitPaymentService;
    @Autowired PaymentRepository paymentRepository;
    @Autowired CouponRepository couponRepository;

    @BeforeEach
    void init() {
        Address address = createAddress(createAddressValue("01011112222", "홍길동", "서울", "세종대로","11111", "민들레아파트110동3222호", "우리집"),true);
        Member member = Member.create(MembershipType.BASIC.getText(), "email.com", "1234", "홍길동", "01011112222", true, true);
        member.addAddress(address);
        memberRepository.save(member);

        Product product1 = Product.create(null, true, "상품1", 1000L, false, 100L, "썸네일1", 0L, "1번");
        Product product2 = Product.create(null, true, "상품2", 2000L, false, 100L, "썸네일2", 0L, "2번");
        Product product3 = Product.create(null, true, "상품3", 3000L, false, 100L, "썸네일3", 0L, "3번");
        Product product4 = Product.create(null, true, "상품4", 4000L, false, 100L, "썸네일4", 0L, "4번");
        productRepository.saveAll(List.of(product1, product2, product3, product4));

        Coupon coupon1 = Coupon.create(product1, "상품1쿠폰", 500L, 10L, 1000L, LocalDateTime.of(2024, 1, 1, 12, 0, 0), LocalDateTime.of(2025, 1, 1, 12, 0, 0));
        Coupon coupon2 = Coupon.create(product2, "상품2쿠폰", 500L, 10L, 1000L, LocalDateTime.of(2024, 1,1,12,0,0), LocalDateTime.of(2025, 1,1,12, 0, 0));
        Coupon coupon3 = Coupon.create(product3, "상품3쿠폰", 500L, 10L, 1000L, LocalDateTime.of(2024, 1,1,12,0,0), LocalDateTime.of(2025, 1,1,12, 0, 0));
        Coupon coupon4 = Coupon.create(product4, "상품4쿠폰", 500L, 10L, 1000L, LocalDateTime.of(2024, 1,1,12,0,0), LocalDateTime.of(2025, 1,1,12, 0, 0));

        MemberCoupon memberCoupon1 = MemberCoupon.create(member, coupon1, true, LocalDateTime.now(), coupon1.getEndDate());
        MemberCoupon memberCoupon2 = MemberCoupon.create(member, coupon1, true, LocalDateTime.now(), coupon2.getEndDate());
        MemberCoupon memberCoupon3 = MemberCoupon.create(member, coupon1, true, LocalDateTime.now(), coupon3.getEndDate());
        MemberCoupon memberCoupon4 = MemberCoupon.create(member, coupon1, true, LocalDateTime.now(), coupon4.getEndDate());

        coupon1.addMemberCoupon(memberCoupon1);
        coupon2.addMemberCoupon(memberCoupon2);
        coupon3.addMemberCoupon(memberCoupon3);
        coupon4.addMemberCoupon(memberCoupon4);
        couponRepository.saveAll(List.of(coupon1, coupon2, coupon3, coupon4));


        OrderDetail orderDetail1 = OrderDetail.create(null, null, product1, "orderNo1", memberCoupon1, 1000L, 1L, null, PAYMENT_COMPLETED.getCode());
        OrderDetail orderDetail2 = OrderDetail.create(null, null, product2, "orderNo1", memberCoupon2, 2000L, 1L, null, PAYMENT_COMPLETED.getCode());
        OrderDetail orderDetail3 = OrderDetail.create(null, null, product3, "orderNo1", memberCoupon3, 3000L, 1L, null, PAYMENT_COMPLETED.getCode());

        Order order1 = createOrder(member, address, "상품1 외 2건", "orderNo1", 6000L, 1500L, 1500L, "tid1", List.of(orderDetail1, orderDetail2, orderDetail3), null);

        orderCommendRepository.save(order1);
    }
    private Address createAddress(AddressValue addressValue, Boolean isDefault) {
        return Address.create(addressValue, isDefault);
    }

    private AddressValue createAddressValue(String phoneNo, String name, String city, String street, String zipcode, String detail, String alias) {
        return AddressValue.create(phoneNo, name, city, street, zipcode, detail, alias);
    }

    private Order createOrder(Member member, Address address, String orderName, String orderNo, Long totalPrice, Long realPrice, Long totalDiscount, String tid, List<OrderDetail> orderDetails, Payment payment) {
        Order order = Order.create(member, address, orderName, orderNo, totalPrice, realPrice, totalDiscount, tid);

        // order <-> orderDetail 연관관계
        orderDetails.forEach(order::addOrderDetail);

        return order;
    }

    @DisplayName("결제를 취소하면 order_detail들도 모두 삭제(소프트삭제) 된다")
    @Test
    void cancel(){
        //given
        Order order = orderQueryRepository.findByOrderNo("orderNo1");
        List<OrderDetail> orderDetails = order.getOrderDetails();
        Long od1Id = orderDetails.get(0).getId();
        Long od2Id = orderDetails.get(1).getId();
        Long od3Id = orderDetails.get(2).getId();

        //when
        quitPaymentService.cancel(order.getOrderNo());

        //then
        assertThatThrownBy(() -> orderQueryRepository.findByOrderNo(order.getOrderNo())).isInstanceOf(JpaObjectRetrievalFailureException.class);
        assertThatThrownBy(() -> orderDetailQueryRepository.findById(od1Id)).isInstanceOf(JpaObjectRetrievalFailureException.class);
        assertThatThrownBy(() -> orderDetailQueryRepository.findById(od2Id)).isInstanceOf(JpaObjectRetrievalFailureException.class);
        assertThatThrownBy(() -> orderDetailQueryRepository.findById(od3Id)).isInstanceOf(JpaObjectRetrievalFailureException.class);
    }

    @DisplayName("결제를 취소하면 재고와 쿠폰 상태가 다시 원복된다.")
    @Test
    void cancel_rollbackCouponAndStock(){
        //given
        String orderNo = "orderNo1";
        Order order = orderQueryRepository.findByOrderNo(orderNo);
        List<Long> beforeStockList = order.getOrderDetails().stream().map(o -> o.getProduct().getStock()).toList();

        //when
        quitPaymentService.cancel(orderNo);

        //then
        List<Long> afterStockList = order.getOrderDetails().stream().map(o -> o.getProduct().getStock()).toList();
        List<Boolean> afterCouponUseStateList = order.getOrderDetails().stream().map(o -> o.getMemberCoupon().getIsUsed()).toList();

        assertThat(afterCouponUseStateList).containsExactlyInAnyOrder(false, false, false);
        assertThat(afterStockList).containsExactlyInAnyOrder(beforeStockList.get(0)+1L, beforeStockList.get(1)+1L, beforeStockList.get(2)+1L);
    }
}