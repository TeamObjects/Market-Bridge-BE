package com.objects.marketbridge.product.infra;

import com.objects.marketbridge.common.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

}