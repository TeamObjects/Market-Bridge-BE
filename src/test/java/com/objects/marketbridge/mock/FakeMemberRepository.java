package com.objects.marketbridge.mock;

import com.objects.marketbridge.common.domain.Member;
import com.objects.marketbridge.member.service.port.MemberRepository;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FakeMemberRepository implements MemberRepository {

    private Long autoGeneratedId = 0L;
    private List<Member> data = new ArrayList<>();

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public Member findByEmail(String email) {
        return null;
    }

    @Override
    public Optional<Member> findOptionalByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Member findById(Long id) {
        return null;
    }

    @Override
    public Member save(Member member) {
        if (member.getId() == null || member.getId() == 0) {
            ReflectionTestUtils.setField(member, "id", ++autoGeneratedId, Long.class);
            data.add(member);
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), member.getId()));
            data.add(member);
        }
        return member;
    }

    @Override
    public Member findByIdWithAddresses(Long id) {
        return null;
    }

    @Override
    public void deleteAllInBatch() {

    }
}
