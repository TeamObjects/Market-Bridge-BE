package com.objects.marketbridge.order.mock;

import com.objects.marketbridge.product.domain.Product;
import com.objects.marketbridge.product.infra.ProductRepository;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FakeProductRepository implements ProductRepository {

    private Long autoGeneratedId = 0L;
    private List<Product> data = new ArrayList<>();

    @Override
    public Product findById(Long id) {
        return null;
    }

    @Override
    public List<Product> findAllById(List<Long> ids) {
        return null;
    }

    @Override
    public List<Product> findByName(String name) {
        return null;
    }

    @Override
    public void deleteAllInBatch() {
        autoGeneratedId = 0L;
        data.clear();
    }

    @Override
    public Product save(Product product) {
        if (product.getId() == null || product.getId() == 0) {
            ReflectionTestUtils.setField(product, "id", ++autoGeneratedId, Long.class);
            data.add(product);
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), product.getId()));
            data.add(product);
        }
        return product;
    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public void saveAll(List<Product> products) {

    }

    @Override
    public void delete(Product product) {

    }
}
