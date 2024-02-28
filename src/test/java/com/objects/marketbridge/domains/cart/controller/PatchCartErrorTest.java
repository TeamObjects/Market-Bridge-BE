package com.objects.marketbridge.domains.cart.controller;

import com.objects.marketbridge.common.RestDocsSupportWebAppContext;
import com.objects.marketbridge.common.security.annotation.WithMockCustomUser;
import com.objects.marketbridge.domains.cart.controller.dto.CreateCartHttp;
import com.objects.marketbridge.domains.cart.service.AddToCartService;
import com.objects.marketbridge.domains.cart.service.DeleteCartService;
import com.objects.marketbridge.domains.cart.service.GetCartListService;
import com.objects.marketbridge.domains.cart.service.UpdateCartService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ActiveProfiles("test")
@WebMvcTest(CartController.class)
public class PatchCartErrorTest extends RestDocsSupportWebAppContext {

    @MockBean
    AddToCartService addToCartService;
    @MockBean
    GetCartListService getCartListService;
    @MockBean
    UpdateCartService updateCartService;
    @MockBean
    DeleteCartService deleteCartService;

    @DisplayName("[PATCH/carts] 입력값 유효성 에러")
    @Test
    @WithMockCustomUser
    void addToCart1() throws Exception {
        // given
        CreateCartHttp.Request request = CreateCartHttp.Request.create(null, 1L, false);

        // when
        MockHttpServletRequestBuilder requestBuilder =
                patch("/carts/cartId")
                        .header(HttpHeaders.AUTHORIZATION, "bearer AccessToken")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("cart-add-invalid-input-error",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }
}