package com.objects.marketbridge.domain.delivery.repository;

import com.objects.marketbridge.domain.delivery.domain.Delivery;
import com.objects.marketbridge.domain.delivery.service.port.DeliveryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DeliveryRepositoryImpl implements DeliveryRepository {

    private final DeliveryJpaRepository deliveryJpaRepository;

    @Override
    public Delivery findById(Long id) {
        return deliveryJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Delivery save(Delivery delivery) {
        return deliveryJpaRepository.save(delivery);
    }

    @Override
    public List<Delivery> saveAll(List<Delivery> deliveries) {
        return deliveryJpaRepository.saveAll(deliveries);
    }

    @Override
    public List<Delivery> findAll() {
        return deliveryJpaRepository.findAll();
    }

    @Override
    public List<Delivery> findAllInIds(List<Long> ids) {
        return deliveryJpaRepository.findAllById(ids);
    }
}