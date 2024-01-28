package com.objects.marketbridge.domain.product.infra;

import com.objects.marketbridge.common.domain.Coupon;

import java.util.List;

public interface CouponRepository {
    Coupon findById(Long id);

    List<Coupon> findAllByIds(List<Long> ids);

    void save(Coupon coupon);

    void saveAll(List<Coupon> coupons);

    List<Coupon> findAll();
}