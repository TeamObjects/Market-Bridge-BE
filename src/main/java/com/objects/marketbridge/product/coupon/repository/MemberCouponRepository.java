package com.objects.marketbridge.product.coupon.repository;

import com.objects.marketbridge.common.infra.entity.MemberCoupon;

import java.util.List;

public interface MemberCouponRepository {
    MemberCoupon findById(Long id);

    MemberCoupon findByMember_IdAndCoupon_Id(Long MemberId, Long CouponId);

    MemberCoupon save(MemberCoupon memberCoupon);

    List<MemberCoupon> saveAll(List<MemberCoupon> memberCoupons);

    List<MemberCoupon> findAll();


}
