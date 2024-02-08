package com.objects.marketbridge.review.infra;

import com.objects.marketbridge.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {

//    List<Review> findAllByProductId(Long productId);

    Page<Review> findAllByProductId(Long productId, Pageable pageable);

    Page<Review> findAllByMemberId(Long memberId, Pageable pageable);

    Long countByProductId(Long productId);

    Long countByMemberId(Long memberId);
}
