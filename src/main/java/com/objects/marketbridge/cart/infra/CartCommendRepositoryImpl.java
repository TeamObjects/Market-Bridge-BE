package com.objects.marketbridge.cart.infra;

import com.objects.marketbridge.cart.domain.Cart;
import com.objects.marketbridge.cart.service.port.CartCommendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CartCommendRepositoryImpl implements CartCommendRepository {

    private final CartJpaRepository cartJpaRepository;

    @Override
    public Cart save(Cart cart) {
        return cartJpaRepository.save(cart);
    }
}