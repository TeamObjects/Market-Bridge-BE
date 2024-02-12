package com.objects.marketbridge.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.objects.marketbridge.payment.domain.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class KakaoPayOrderResponse {
    private String tid;
    private String cid;

    @JsonProperty("status")
    private String kakaoStatus;

    @JsonProperty("partner_order_id")
    private String partnerOrderId;

    @JsonProperty("partner_user_id")
    private String partnerUserId;

    @JsonProperty("payment_method_type")
    private String paymentMethodType;

    private Amount amount;

    @JsonProperty("canceled_amount")
    private CanceledAmount canceledAmount;

    @JsonProperty("cancel_available_amount")
    private CanceledAvailableAmount canceledAvailableAmount;

    @JsonProperty("item_name")
    private String itemName;

    private String quantity;

    @JsonProperty("approved_at")
    private LocalDateTime approvedAt;

    @JsonProperty("canceled_at")
    private LocalDateTime canceledAt;

    @JsonProperty("selected_card_info")
    private CardInfo selectedCardInfo;

    @JsonProperty("payment_action_details")
    private List<PaymentActionDetail> paymentActionDetails;

    @Builder
    public KakaoPayOrderResponse(String tid, String cid, String kakaoStatus, String partnerOrderId, String partnerUserId, String paymentMethodType, Amount amount, CanceledAmount canceledAmount, CanceledAvailableAmount canceledAvailableAmount, String itemName, String quantity, LocalDateTime approvedAt, LocalDateTime canceledAt, CardInfo selectedCardInfo, List<PaymentActionDetail> paymentActionDetails) {
        this.tid = tid;
        this.cid = cid;
        this.kakaoStatus = kakaoStatus;
        this.partnerOrderId = partnerOrderId;
        this.partnerUserId = partnerUserId;
        this.paymentMethodType = paymentMethodType;
        this.amount = amount;
        this.canceledAmount = canceledAmount;
        this.canceledAvailableAmount = canceledAvailableAmount;
        this.itemName = itemName;
        this.quantity = quantity;
        this.approvedAt = approvedAt;
        this.canceledAt = canceledAt;
        this.selectedCardInfo = selectedCardInfo;
        this.paymentActionDetails = paymentActionDetails;
    }
}
