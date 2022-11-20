package com.study.redis.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import java.time.OffsetDateTime;

@RedisHash(value = "member")
@ToString
@Getter
@NoArgsConstructor
public class Member {

    private Long id;

    private String name;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    @Builder
    public Member(Long id, String name, OffsetDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

}
