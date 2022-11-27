package com.study.redis.service;

import com.study.redis.domain.Member;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class MemberService {

    @Cacheable(value = "serviceMembers")
    public Member getCacheMember() {
        return getMember();
    }

    @CacheEvict(value = "serviceMembers")
    public void deleteCacheMember() {
        return;
    }

    private Member getMember() {
        return Member.builder()
                .id(10L)
                .name("cacheable member")
                .createdAt(OffsetDateTime.now())
                .build();
    }

}
