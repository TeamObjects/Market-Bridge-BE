package com.objects.marketbridge.domain.product.infra;

import com.objects.marketbridge.common.domain.Option;
import com.objects.marketbridge.domain.product.service.port.OptionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OptionRepositoryImpl implements OptionRepository {

    private final OptionJpaRepository optionJpaRepository;

    @Override
    public void save(Option option) {
        optionJpaRepository.save(option);
    }

    @Override
    public Option findById(Long id) {
        return optionJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Option findByName(String name) {
        return optionJpaRepository.findByName(name);
    }
}