package com.daeuun.redis;

import com.daeuun.redis.entity.redis.AccessToken;
import com.daeuun.redis.repository.AccessTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataRedisTest
@Import(EmbeddedRedisConfig.class)
@ActiveProfiles("test")
class AccessTokenRedisRepositoryTest {
	@Autowired
	AccessTokenRepository accessTokenRepository;
	@BeforeEach
	void clear(){
		accessTokenRepository.deleteAll();
	}
	@DisplayName("save")
	@Test
	void save() throws Exception {
		//given
		String accessToken = "accessToken";
		String name = "name";
		Long timeout = 3000L;
		LocalDateTime time = LocalDateTime.of(2022, 12, 18, 0, 0);
		AccessToken logoutAccessToken = AccessToken.createAccessToken(accessToken, name, timeout, time);

		//when
		accessTokenRepository.save(logoutAccessToken);

		//then
		AccessToken token = accessTokenRepository.findById(accessToken).get();

		assertAll(
				() -> assertEquals(accessToken, token.getId()),
				() -> assertEquals(name, token.getName()),
				() -> assertEquals(timeout/1000, token.getTimeout())
		);
	}
	@DisplayName("수정")
	@Test
	void 수정() throws Exception {
		//given
		String accessToken = "accessToken";
		String name = "name";
		Long timeout = 3000L;
		LocalDateTime time = LocalDateTime.of(2022, 12, 18, 0, 0);
		AccessToken logoutAccessToken = AccessToken.createAccessToken(accessToken, name, timeout, time);
		accessTokenRepository.save(logoutAccessToken);

		//when
		AccessToken token = accessTokenRepository.findById(accessToken).get();
		token.updateToken(name, LocalDateTime.of(2022, 12, 19, 0, 0));
		accessTokenRepository.save(token);

		//then
		AccessToken refreshToken = accessTokenRepository.findById(accessToken).get();

		assertAll(
				() -> assertEquals(accessToken, refreshToken.getId()),
				() -> assertEquals(name, refreshToken.getName()),
				() -> assertEquals(timeout/1000, refreshToken.getTimeout())
		);
	}
}