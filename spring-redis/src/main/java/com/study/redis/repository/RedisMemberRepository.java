package com.study.redis.repository;

import com.study.redis.domain.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisMemberRepository extends CrudRepository<Member, Long> {
}
