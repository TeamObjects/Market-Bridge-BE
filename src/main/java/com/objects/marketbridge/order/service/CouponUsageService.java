package com.objects.marketbridge.order.service;

import com.objects.marketbridge.common.infra.entity.MemberCoupon;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CouponUsageService {

    public void applyCouponUsage(List<MemberCoupon> memberCoupons, Boolean isUsed, LocalDateTime usedDate) {

        memberCoupons.forEach(mc -> mc.applyCouponUsage(isUsed, usedDate));
    }

}
