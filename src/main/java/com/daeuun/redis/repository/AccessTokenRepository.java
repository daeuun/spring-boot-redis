package com.daeuun.redis.repository;

import com.daeuun.redis.entity.redis.AccessToken;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccessTokenRepository extends CrudRepository<AccessToken, String> {
    // @Indexed 사용한 필드 사용 가능
    Optional<AccessToken> findByName(String name);
}
