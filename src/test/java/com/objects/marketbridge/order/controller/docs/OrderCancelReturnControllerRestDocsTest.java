package com.objects.marketbridge.order.controller.docs;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.objects.marketbridge.common.security.annotation.WithMockCustomUser;
import com.objects.marketbridge.common.service.port.DateTimeHolder;
import com.objects.marketbridge.member.domain.Member;
import com.objects.marketbridge.member.domain.MembershipType;
import com.objects.marketbridge.member.service.port.MemberRepository;
import com.objects.marketbridge.order.controller.OrderCancelReturnController;
import com.objects.marketbridge.order.service.OrderCancelReturnService;
import com.objects.marketbridge.order.service.dto.RequestCancelDto;
import com.objects.marketbridge.order.service.dto.RequestReturnDto;
import com.objects.marketbridge.order.service.port.OrderDtoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ActiveProfiles("test")
@WebMvcTest(OrderCancelReturnController.class)
@ExtendWith(RestDocumentationExtension.class)
public class OrderCancelReturnControllerRestDocsTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    OrderCancelReturnService orderCancelReturnService;
    @MockBean
    OrderDtoRepository orderDtoRepository;
    @MockBean
    MemberRepository memberRepository;
    @MockBean
    DateTimeHolder dateTimeHolder;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();
    }

    @Test
    @DisplayName("주문 취소 요청 API")
    @WithMockCustomUser
    public void requestCancel() throws Exception {
        // given
        RequestCancelDto.Response response = RequestCancelDto.Response.builder()
                .cancelRefundInfo(
                        RequestCancelDto.CancelRefundInfo.builder()
                                .deliveryFee(0L)
                                .refundFee(0L)
                                .totalPrice(2000L)
                                .discountPrice(0L)
                                .build()
                )
                .productInfo(
                        RequestCancelDto.ProductInfo.builder()
                                .name("빵빵이키링")
                                .quantity(2L)
                                .price(1000L)
                                .image("빵빵이썸네일")
                                .build()
                )
                .build();

        Member member = Member.builder()
                .membership(MembershipType.WOW.getText())
                .build();

        given(memberRepository.findById(anyLong())).willReturn(member);
        given(orderCancelReturnService.findCancelInfo(anyLong(), anyLong(), anyString()))
                .willReturn(response);

        // when // then
        mockMvc.perform(
                        get("/orders/cancel-flow")
                                .header(HttpHeaders.AUTHORIZATION, "bearer AccessToken")
                                .accept(MediaType.APPLICATION_JSON)
                                .param("orderDetailId", "1")
                                .param("numberOfCancellation", "2")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("order-cancel-request",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("orderDetailId")
                                        .description("주문 상세 ID"),
                                parameterWithName("numberOfCancellation")
                                        .description("취소 수량")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .description("응답 데이터"),
                                fieldWithPath("data.productInfo").type(JsonFieldType.OBJECT)
                                        .description("취소 상품 정보"),
                                fieldWithPath("data.productInfo.quantity").type(JsonFieldType.NUMBER)
                                        .description("취소 상품 수량"),
                                fieldWithPath("data.productInfo.name").type(JsonFieldType.STRING)
                                        .description("취소 상품 이름"),
                                fieldWithPath("data.productInfo.price").type(JsonFieldType.NUMBER)
                                        .description("취소 상품 가격"),
                                fieldWithPath("data.productInfo.image").type(JsonFieldType.STRING)
                                        .description("취소 상품 썸네일"),
                                fieldWithPath("data.cancelRefundInfo").type(JsonFieldType.OBJECT)
                                        .description("취소 상품 썸네일"),
                                fieldWithPath("data.cancelRefundInfo.deliveryFee").type(JsonFieldType.NUMBER)
                                        .description("환불 배송비"),
                                fieldWithPath("data.cancelRefundInfo.refundFee").type(JsonFieldType.NUMBER)
                                        .description("환불 금액"),
                                fieldWithPath("data.cancelRefundInfo.discountPrice").type(JsonFieldType.NUMBER)
                                        .description("할인 금액"),
                                fieldWithPath("data.cancelRefundInfo.totalPrice").type(JsonFieldType.NUMBER)
                                        .description("상품 전체 금액")
                        )));
    }

    @Test
    @DisplayName("주문 반품 요청 API")
    @WithMockCustomUser
    public void requestReturnOrder() throws Exception {
        // given
        RequestReturnDto.Response response = RequestReturnDto.Response.builder()
                .returnRefundInfo(
                        RequestReturnDto.ReturnRefundInfo.builder()
                                .returnFee(0L)
                                .deliveryFee(0L)
                                .productTotalPrice(2000L)
                                .build()
                )
                .productInfo(
                        RequestReturnDto.ProductInfo.builder()
                                .quantity(2L)
                                .name("빵빵이키링")
                                .price(1000L)
                                .image("빵빵이썸네일")
                                .build()
                )
                .build();

        Member member = Member.builder()
                .membership(MembershipType.WOW.getText())
                .build();

        given(memberRepository.findById(anyLong())).willReturn(member);
        given(orderCancelReturnService.findReturnInfo(anyLong(), anyLong(), anyString()))
                .willReturn(response);

        // when // then
        mockMvc.perform(
                        get("/orders/return-flow")
                                .header(HttpHeaders.AUTHORIZATION, "bearer AccessToken")
                                .accept(MediaType.APPLICATION_JSON)
                                .param("orderDetailId", "1")
                                .param("numberOfReturns", "2")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("order-return-request",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("orderDetailId")
                                        .description("주문 상세 ID"),
                                parameterWithName("numberOfReturns")
                                        .description("취소 수량")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .description("응답 데이터"),
                                fieldWithPath("data.productInfo").type(JsonFieldType.OBJECT)
                                        .description("반품 상품 정보"),
                                fieldWithPath("data.productInfo.quantity").type(JsonFieldType.NUMBER)
                                        .description("반품 상품 수량"),
                                fieldWithPath("data.productInfo.name").type(JsonFieldType.STRING)
                                        .description("반품 상품 이름"),
                                fieldWithPath("data.productInfo.price").type(JsonFieldType.NUMBER)
                                        .description("반품 상품 가격"),
                                fieldWithPath("data.productInfo.image").type(JsonFieldType.STRING)
                                        .description("반품 상품 썸네일"),
                                fieldWithPath("data.returnRefundInfo").type(JsonFieldType.OBJECT)
                                        .description("반품 환불 정보"),
                                fieldWithPath("data.returnRefundInfo.deliveryFee").type(JsonFieldType.NUMBER)
                                        .description("환불 배송비"),
                                fieldWithPath("data.returnRefundInfo.returnFee").type(JsonFieldType.NUMBER)
                                        .description("반품 배송비"),
                                fieldWithPath("data.returnRefundInfo.productTotalPrice").type(JsonFieldType.NUMBER)
                                        .description("환불(상품) 금액")
                        )));
    }

//    @Test
//    @DisplayName("주문 취소 확정 API")
//    public void confirmCancelReturn() throws Exception {
//        // given
//        ConfirmCancelReturnHttp.OrderDetailInfo orderDetailInfo1 = ConfirmCancelReturnHttp.OrderDetailInfo.builder()
//                .orderDetailId(1L)
//                .numberOfCancellation(1L)
//                .build();
//        ConfirmCancelReturnHttp.OrderDetailInfo orderDetailInfo2 = ConfirmCancelReturnHttp.OrderDetailInfo.builder()
//                .orderDetailId(2L)
//                .numberOfCancellation(2L)
//                .build();
//        List<ConfirmCancelReturnHttp.OrderDetailInfo> orderDetailInfos = List.of(orderDetailInfo1, orderDetailInfo2);
//
//        ConfirmCancelReturnHttp.Request request = ConfirmCancelReturnHttp.Request.builder()
//                .orderDetailInfos(orderDetailInfos)
//                .cancelReason("단순변심")
//                .build();
//
//
//        LocalDateTime cancellationDate = LocalDateTime.of(2024, 1, 18, 12, 26);
//        LocalDateTime refundProcessedAt = LocalDateTime.of(2024, 1, 18, 12, 26);
//
//        given(orderCancelReturnService.confirmCancelReturn(any(ConfirmCancelReturnDto.Request.class), any(DateTimeHolder.class)))
//                .willReturn(ConfirmCancelReturnDto.Response.builder()
//                        .orderId(1L)
//                        .orderNo("ORD001")
//                        .totalPrice(300L)
//                        .cancellationDate(cancellationDate)
//                        .refundInfo(ConfirmCancelReturnDto.RefundInfo.of(
//                                RefundDto.builder()
//                                        .totalRefundAmount(10000L)
//                                        .refundMethod("card")
//                                        .refundProcessedAt(refundProcessedAt)
//                                        .build())
//                        )
//                        .cancelledItems(List.of(
//                                        ConfirmCancelReturnDto.ProductInfo.builder()
//                                                .productId(1L)
//                                                .name("빵빵이 키링")
//                                                .productNo("P123456")
//                                                .price(10000L)
//                                                .quantity(2L)
//                                                .build(),
//                                        ConfirmCancelReturnDto.ProductInfo.builder()
//                                                .productId(2L)
//                                                .name("옥지얌 키링")
//                                                .productNo("P2345667")
//                                                .price(20000L)
//                                                .quantity(3L)
//                                                .build()
//                                )
//                        )
//                        .build()
//                );
//
//        // when // then
//        mockMvc.perform(
//                        post("/orders/cancel-return-flow/thank-you")
//                                .content(objectMapper.writeValueAsString(request))
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("order-cancel-return",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("orderDetailInfos[]").type(JsonFieldType.ARRAY)
//                                        .description("주문 상세 정보 리스트"),
//                                fieldWithPath("orderDetailInfos[].orderDetailId").type(JsonFieldType.NUMBER)
//                                        .description("주문 상세 ID"),
//                                fieldWithPath("orderDetailInfos[].numberOfCancellation").type(JsonFieldType.NUMBER)
//                                        .description("주문 취소 개수"),
//                                fieldWithPath("cancelReason").type(JsonFieldType.STRING)
//                                        .description("상품 판매상태")
//                        ),
//                        responseFields(
//                                fieldWithPath("code").type(JsonFieldType.NUMBER)
//                                        .description("코드"),
//                                fieldWithPath("status").type(JsonFieldType.STRING)
//                                        .description("상태"),
//                                fieldWithPath("message").type(JsonFieldType.STRING)
//                                        .description("메시지"),
//                                fieldWithPath("data").type(JsonFieldType.OBJECT)
//                                        .description("응답 데이터"),
//                                fieldWithPath("data.orderId").type(JsonFieldType.NUMBER)
//                                        .description("주문 ID"),
//                                fieldWithPath("data.orderNo").type(JsonFieldType.STRING)
//                                        .description("주문 번호"),
//                                fieldWithPath("data.totalPrice").type(JsonFieldType.NUMBER)
//                                        .description("주문 총 가격"),
//                                fieldWithPath("data.cancellationDate").type(JsonFieldType.ARRAY)
//                                        .description("주문 취소 시간"),
//                                fieldWithPath("data.cancelledItems").type(JsonFieldType.ARRAY)
//                                        .description("상품 이름"),
//                                fieldWithPath("data.cancelledItems[].productId").type(JsonFieldType.NUMBER)
//                                        .description("취소된 상품의 제품 ID"),
//                                fieldWithPath("data.cancelledItems[].productNo").type(JsonFieldType.STRING)
//                                        .description("취소된 상품의 제품 번호"),
//                                fieldWithPath("data.cancelledItems[].name").type(JsonFieldType.STRING)
//                                        .description("취소된 상품의 이름"),
//                                fieldWithPath("data.cancelledItems[].price").type(JsonFieldType.NUMBER)
//                                        .description("취소된 상품의 가격"),
//                                fieldWithPath("data.cancelledItems[].quantity").type(JsonFieldType.NUMBER)
//                                        .description("취소된 주문 수량"),
//                                fieldWithPath("data.refundInfo").type(JsonFieldType.OBJECT)
//                                        .description("환불 정보"),
//                                fieldWithPath("data.refundInfo.totalRefundAmount").type(JsonFieldType.NUMBER)
//                                        .description("환불 총 금액"),
//                                fieldWithPath("data.refundInfo.refundMethod").type(JsonFieldType.STRING)
//                                        .description("환불 방법"),
//                                fieldWithPath("data.refundInfo.refundProcessedAt").type(JsonFieldType.ARRAY)
//                                        .description("환불 시간")
//                        )
//                ));
//    }




//
//    @Test
//    @DisplayName("주문 취소/반품 리스트 반환 API")
//    public void getCancelReturnList() throws Exception {
//        // given
//        Member member = Member.builder().build();
//
//        Order order1 = Order.builder()
//                .member(member)
//                .orderNo("123")
//                .build();
//
//        Order order2 = Order.builder()
//                .member(member)
//                .orderNo("456")
//                .build();
//
//        Product product1 = Product.builder()
//                .productNo("1")
//                .name("옷")
//                .price(1000L)
//                .build();
//        Product product2 = Product.builder()
//                .productNo("2")
//                .name("신발")
//                .price(2000L)
//                .build();
//        Product product3 = Product.builder()
//                .productNo("3")
//                .name("바지")
//                .price(3000L)
//                .build();
//
//        OrderDetail orderDetail1 = OrderDetail.builder()
//                .order(order1)
//                .product(product1)
//                .quantity(1L)
//                .price(product1.getPrice())
//                .orderNo(order1.getOrderNo())
//                .statusCode(RETURN_COMPLETED.getCode())
//                .build();
//        OrderDetail orderDetail2 = OrderDetail.builder()
//                .order(order1)
//                .product(product2)
//                .quantity(2L)
//                .orderNo(order1.getOrderNo())
//                .price(product2.getPrice())
//                .statusCode(ORDER_CANCEL.getCode())
//                .build();
//        OrderDetail orderDetail3 = OrderDetail.builder()
//                .order(order2)
//                .product(product3)
//                .quantity(3L)
//                .orderNo(order2.getOrderNo())
//                .price(product3.getPrice())
//                .statusCode(ORDER_CANCEL.getCode())
//                .build();
//        OrderDetail orderDetail4 = OrderDetail.builder()
//                .order(order2)
//                .product(product2)
//                .quantity(4L)
//                .orderNo(order2.getOrderNo())
//                .statusCode(DELIVERY_ING.getCode())
//                .price(product2.getPrice())
//                .build();
//
//        order1.addOrderDetail(orderDetail1);
//        order1.addOrderDetail(orderDetail2);
//        order2.addOrderDetail(orderDetail3);
//        order2.addOrderDetail(orderDetail4);
//
//        productRepository.saveAll(List.of(product1, product2, product3));
//        memberRepository.save(member);
//        orderCommendRepository.save(order1);
//        orderCommendRepository.save(order2);
//
//        Page<GetCancelReturnListDtio.Response> orderCancelReturnListResponsePage = orderDtoRepository.findOrdersByMemberId(member.getId(), PageRequest.of(0, 3));
//
//        LocalDateTime orderDate1 = LocalDateTime.of(2024, 2, 5, 2, 11);
//        LocalDateTime orderDate2 = LocalDateTime.of(2024, 2, 5, 2, 12);
//        LocalDateTime cancelReceiptDate1 = LocalDateTime.of(2024, 2, 5, 2, 13);
//        LocalDateTime cancelReceiptDate2 = LocalDateTime.of(2024, 2, 5, 2, 14);
//        GetCancelReturnListDtio.Response dtio1 = GetCancelReturnListDtio.Response.builder()
//                .cancelReceiptDate(cancelReceiptDate1)
//                .orderDate(orderDate1)
//                .orderNo("123")
//                .build();
//        GetCancelReturnListDtio.Response dtio2 = GetCancelReturnListDtio.Response.builder()
//                .cancelReceiptDate(cancelReceiptDate2)
//                .orderDate(orderDate2)
//                .orderNo("123")
//                .build();
//        GetCancelReturnListDtio.OrderDetailInfo orderDetailInfo1 = GetCancelReturnListDtio.OrderDetailInfo.builder()
//                .orderNo(orderDetail1.getOrderNo())
//                .productId(orderDetail1.getProduct().getId())
//                .productNo(orderDetail1.getProduct().getProductNo())
//                .name(orderDetail1.getProduct().getName())
//                .price(orderDetail1.getPrice())
//                .quantity(orderDetail1.getQuantity())
//                .orderStatus(orderDetail1.getStatusCode())
//                .build();
//        GetCancelReturnListDtio.OrderDetailInfo orderDetailInfo2 = GetCancelReturnListDtio.OrderDetailInfo.builder()
//                .orderNo(orderDetail2.getOrderNo())
//                .productId(orderDetail2.getProduct().getId())
//                .productNo(orderDetail2.getProduct().getProductNo())
//                .name(orderDetail2.getProduct().getName())
//                .price(orderDetail2.getPrice())
//                .quantity(orderDetail2.getQuantity())
//                .orderStatus(orderDetail2.getStatusCode())
//                .build();
//        GetCancelReturnListDtio.OrderDetailInfo orderDetailInfo3 = GetCancelReturnListDtio.OrderDetailInfo.builder()
//                .orderNo(orderDetail3.getOrderNo())
//                .productId(orderDetail3.getProduct().getId())
//                .productNo(orderDetail3.getProduct().getProductNo())
//                .name(orderDetail3.getProduct().getName())
//                .price(orderDetail3.getPrice())
//                .quantity(orderDetail3.getQuantity())
//                .orderStatus(orderDetail3.getStatusCode())
//                .build();
//        GetCancelReturnListDtio.OrderDetailInfo orderDetailInfo4 = GetCancelReturnListDtio.OrderDetailInfo.builder()
//                .orderNo(orderDetail4.getOrderNo())
//                .productId(orderDetail4.getProduct().getId())
//                .productNo(orderDetail4.getProduct().getProductNo())
//                .name(orderDetail4.getProduct().getName())
//                .price(orderDetail4.getPrice())
//                .quantity(orderDetail4.getQuantity())
//                .orderStatus(orderDetail4.getStatusCode())
//                .build();
//
//        List<GetCancelReturnListDtio.OrderDetailInfo> orderDetailInfos1 = List.of(orderDetailInfo1, orderDetailInfo2);
//        List<GetCancelReturnListDtio.OrderDetailInfo> orderDetailInfos2 = List.of(orderDetailInfo3, orderDetailInfo4);
//
//        dtio1.changeOrderDetailInfos(orderDetailInfos1);
//        dtio2.changeOrderDetailInfos(orderDetailInfos2);
//
//        List<GetCancelReturnListDtio.Response> contents = List.of(dtio1, dtio2);
//
//        PageRequest pageRequest = PageRequest.of(0, 5);
//        given(orderDtoRepository.findOrdersByMemberId(any(Long.class), any(PageRequest.class)))
//                .willReturn(new PageImpl<>(contents, pageRequest, 2));
//
//        // when // then
//        mockMvc.perform(
//                        get("/orders/cancel-return/list")
//                                .accept(MediaType.APPLICATION_JSON)
//                                .param("memberId", "1")
//                                .param("page", "0")
//                                .param("size", "5")
//
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("order-cancel-return-list",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        queryParameters(
//                                parameterWithName("memberId")
//                                        .description("유저 ID"),
//                                parameterWithName("page")
//                                        .description("페이지 번호"),
//                                parameterWithName("size")
//                                        .description("사이즈 크기")
//                        ),
//                        responseFields(
//                                fieldWithPath("code").type(JsonFieldType.NUMBER)
//                                        .description("코드"),
//                                fieldWithPath("status").type(JsonFieldType.STRING)
//                                        .description("상태"),
//                                fieldWithPath("message").type(JsonFieldType.STRING)
//                                        .description("메시지"),
//                                fieldWithPath("data").type(JsonFieldType.OBJECT)
//                                        .description("응답 데이터"),
//                                fieldWithPath("data.content[].cancelReceiptDate")
//                                        .description("주문 취소 날짜"),
//                                fieldWithPath("data.content[].orderDate")
//                                        .description("주문 날짜"),
//                                fieldWithPath("data.content[].orderNo").type(JsonFieldType.STRING)
//                                        .description("주문 번호"),
//                                fieldWithPath("data.content[].orderDetailInfos[].orderNo").type(JsonFieldType.STRING)
//                                        .description("주문 번호"),
//                                fieldWithPath("data.content[].orderDetailInfos[].productId").type(JsonFieldType.NUMBER)
//                                        .description("상품 Id"),
//                                fieldWithPath("data.content[].orderDetailInfos[].productNo").type(JsonFieldType.STRING)
//                                        .description("상품 번호"),
//                                fieldWithPath("data.content[].orderDetailInfos[].name").type(JsonFieldType.STRING)
//                                        .description("상품 이름"),
//                                fieldWithPath("data.content[].orderDetailInfos[].price").type(JsonFieldType.NUMBER)
//                                        .description("상품 가격"),
//                                fieldWithPath("data.content[].orderDetailInfos[].quantity").type(JsonFieldType.NUMBER)
//                                        .description("상품 주문 수량"),
//                                fieldWithPath("data.content[].orderDetailInfos[].orderStatus").type(JsonFieldType.STRING)
//                                        .description("주문 상태"),
//                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER)
//                                        .description("페이지 번호"),
//                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER)
//                                        .description("페이지당 요소 수"),
//                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN)
//                                        .description("정렬이 비어 있는지 여부"),
//                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN)
//                                        .description("정렬이 되어 있는지 여부"),
//                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN)
//                                        .description("정렬이 되어 있지 않은지 여부"),
//                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER)
//                                        .description("페이지 오프셋"),
//                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN)
//                                        .description("페이지가 있는지 여부"),
//                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN)
//                                        .description("페이지가 없는지 여부"),
//                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN)
//                                        .description("마지막 페이지 여부"),
//                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER)
//                                        .description("전체 페이지 수"),
//                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER)
//                                        .description("전체 요소 수"),
//                                fieldWithPath("data.size").type(JsonFieldType.NUMBER)
//                                        .description("페이지 크기"),
//                                fieldWithPath("data.number").type(JsonFieldType.NUMBER)
//                                        .description("페이지 번호"),
//                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN)
//                                        .description("정렬이 비어 있는지 여부"),
//                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN)
//                                        .description("정렬이 되어 있는지 여부"),
//                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN)
//                                        .description("정렬이 되어 있지 않은지 여부"),
//                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN)
//                                        .description("첫 페이지 여부"),
//                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER)
//                                        .description("현재 페이지의 요소 수"),
//                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN)
//                                        .description("컨텐츠가 비어 있는지 여부")
//
//
//                        )));
//    }
//
//    @Test
//    @DisplayName("")
//    public void getCancelReturnDetail() throws Exception {
//        // given
//        given(orderCancelReturnService.findCancelReturnDetail(any(List.class), any(String.class), any(DateTimeHolder.class)))
//                .willReturn(GetCancelReturnDetailDto.Response.builder()
//                        .orderDate(LocalDateTime.now())
//                        .cancelDate(LocalDateTime.now())
//                        .orderNo("123")
//                        .cancelReason("빵빵이 기여워")
//                        .productInfos(
//                                List.of(
//                                        GetCancelReturnDetailDto.ProductInfo.builder()
//                                                .productId(1L)
//                                                .productNo("1")
//                                                .name("빵빵이 키링")
//                                                .price(10000L)
//                                                .quantity(2L)
//                                                .build(),
//                                        GetCancelReturnDetailDto.ProductInfo.builder()
//                                                .productId(2L)
//                                                .productNo("2")
//                                                .name("옥지얌 키링")
//                                                .price(20000L)
//                                                .quantity(4L)
//                                                .build()
//                                )
//                        )
//                        .cancelRefundInfo(
//                                GetCancelReturnDetailDto.CancelRefundInfo.builder()
//                                        .deliveryFee(0L)
//                                        .refundFee(0L)
//                                        .discountPrice(5000L)
//                                        .totalPrice(100000L)
//                                        .build()
//                        )
//                        .build()
//                );
//
//        // when // then
//        mockMvc.perform(
//                        RestDocumentationRequestBuilders.get("/orders/cancel-return/{orderNo}", "123")
////                                .param("paymentId", "1")
////                                .param("receiptType", "card")
//                                .param("productIds", "1", "2", "3")
//                                .accept(MediaType.APPLICATION_JSON)
//
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("order-cancel-return-detail",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("orderNo")
//                                        .description("주문 번호")
//                        ),
//                        queryParameters(
//                                parameterWithName("productIds")
//                                        .description("사이즈")
//                        ),
//                        responseFields(
//                                fieldWithPath("code").type(JsonFieldType.NUMBER)
//                                        .description("코드"),
//                                fieldWithPath("status").type(JsonFieldType.STRING)
//                                        .description("상태"),
//                                fieldWithPath("message").type(JsonFieldType.STRING)
//                                        .description("메시지"),
//                                fieldWithPath("data").type(JsonFieldType.OBJECT)
//                                        .description("응답 데이터"),
//                                fieldWithPath("data.orderDate").type(JsonFieldType.ARRAY)
//                                        .description("주문 날짜"),
//                                fieldWithPath("data.cancelDate").type(JsonFieldType.ARRAY)
//                                        .description("주문 취소 날짜"),
//                                fieldWithPath("data.orderNo").type(JsonFieldType.STRING)
//                                        .description("주문 번호"),
//                                fieldWithPath("data.cancelReason").type(JsonFieldType.STRING)
//                                        .description("주문 취소 이유"),
//                                fieldWithPath("data.productInfos").type(JsonFieldType.ARRAY)
//                                        .description("상품 정보 리스트"),
//                                fieldWithPath("data.productInfos[].productId").type(JsonFieldType.NUMBER)
//                                        .description("상품 ID"),
//                                fieldWithPath("data.productInfos[].productNo").type(JsonFieldType.STRING)
//                                        .description("상품 번호"),
//                                fieldWithPath("data.productInfos[].name").type(JsonFieldType.STRING)
//                                        .description("상품 이름"),
//                                fieldWithPath("data.productInfos[].price").type(JsonFieldType.NUMBER)
//                                        .description("주문 가격"),
//                                fieldWithPath("data.productInfos[].quantity").type(JsonFieldType.NUMBER)
//                                        .description("상품 주문 수량"),
//                                fieldWithPath("data.cancelRefundInfo").type(JsonFieldType.OBJECT)
//                                        .description("취소/반품 정보"),
//                                fieldWithPath("data.cancelRefundInfo.deliveryFee").type(JsonFieldType.NUMBER)
//                                        .description("배송 비용"),
//                                fieldWithPath("data.cancelRefundInfo.refundFee").type(JsonFieldType.NUMBER)
//                                        .description("반품 비용"),
//                                fieldWithPath("data.cancelRefundInfo.discountPrice").type(JsonFieldType.NUMBER)
//                                        .description("할인 금액"),
//                                fieldWithPath("data.cancelRefundInfo.totalPrice").type(JsonFieldType.NUMBER)
//                                        .description("상품 할인 전 금액 합계")
//                        )));
//    }
}
