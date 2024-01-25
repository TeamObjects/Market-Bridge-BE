package com.objects.marketbridge.domain.order.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TossErrorResponse {
    private String code;
    private String message;

    @Builder
    public TossErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
