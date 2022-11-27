package com.study.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.redis.domain.Member;
import com.study.redis.repository.RedisMemberRepository;
import com.study.redis.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class RedisCacheTest extends TestConfig {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private RedisMemberRepository redisMemberRepository;

    @BeforeEach
    public void init() {
        // Flush all.
        redisMemberRepository.deleteAll();

        // Create cache with redisTemplate.
        Member redisTemplateMember = Member.builder()
                .id(1L)
                .name("test1")
                .createdAt(OffsetDateTime.now())
                .build();

        redisTemplate.opsForValue().set("test1", redisTemplateMember);

        // Create cache with redisRepository.
        Member repositoryMember = Member.builder()
                .id(2L)
                .name("test2")
                .createdAt(OffsetDateTime.now())
                .build();

        redisMemberRepository.save(repositoryMember);
    }

    @Test
    public void createCache_RedisTemplate() {
        final String cacheKey = "test2";
        Member member = Member.builder()
                .id(2L)
                .name("test2")
                .createdAt(OffsetDateTime.now())
                .build();

        redisTemplate.opsForValue().set(cacheKey, member);

        Member getMember = objectMapper.convertValue(redisTemplate.opsForValue().get(cacheKey), Member.class);

        log.info("Cache: {}", getMember);

        assertThat(getMember.getId()).isEqualTo(member.getId());
        assertThat(getMember.getName()).isEqualTo(member.getName());
        assertThat(getMember.getCreatedAt()).isNotNull();
    }

    @Test
    public void getCache_RedisTemplate() {
        Member getMember = objectMapper.convertValue(redisTemplate.opsForValue().get("test1"), Member.class);

        log.info("Cache: {}", getMember);

        assertThat(getMember.getId()).isEqualTo(1L);
        assertThat(getMember.getName()).isEqualTo("test1");
        assertThat(getMember.getCreatedAt()).isNotNull();
    }

    @Test
    public void deleteCache_RedisTemplate() {
        redisTemplate.delete("test1");

        Member getMember = objectMapper.convertValue(redisTemplate.opsForValue().get("test1"), Member.class);

        log.info("Cache: {}", getMember);

        assertThat(getMember).isNull();
    }

    @Test
    public void createCache_Repository() {
        Member member = Member.builder()
                .id(3L)
                .name("test3")
                .createdAt(OffsetDateTime.now())
                .build();

        redisMemberRepository.save(member);

        Member getMember = redisMemberRepository.findById(3L).get();

        log.info("Cache: {}", getMember);

        assertThat(getMember).isNotNull();
        assertThat(getMember.getId()).isEqualTo(3L);
        assertThat(getMember.getName()).isEqualTo("test3");
        assertThat(getMember.getCreatedAt()).isNotNull();
    }

    @Test
    public void getCache_Repository() throws Exception {
        Member getMember = redisMemberRepository.findById(2L).orElseThrow(IllegalArgumentException::new);

        assertThat(getMember).isNotNull();
        assertThat(getMember.getId()).isEqualTo(2L);
        assertThat(getMember.getName()).isEqualTo("test2");
        assertThat(getMember.getCreatedAt()).isNotNull();
    }

    @Test
    public void deleteCache_Repository() {
        redisMemberRepository.deleteById(2L);

        assertThrows(IllegalArgumentException.class, () -> redisMemberRepository.findById(2L).orElseThrow(IllegalArgumentException::new));
    }

    @Test
    public void getCache_Cacheable() {
        memberService.getCacheMember();
    }

    @Test
    public void deleteCache_CacheEvict() {
        memberService.deleteCacheMember();

        assertThrows(IllegalArgumentException.class, () -> redisMemberRepository.findById(10L).orElseThrow(IllegalArgumentException::new));
    }

}
