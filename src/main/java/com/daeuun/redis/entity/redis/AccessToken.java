package com.daeuun.redis.entity.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@Getter
@RedisHash("Access_token")
@AllArgsConstructor
@Builder
public class AccessToken {

    @Id
    private String id;
    @Indexed
    private String name;
    @TimeToLive
    private Long timeout;
    private LocalDateTime refreshTime;

    public static AccessToken createAccessToken(String accessToken, String name, Long remainingMilliSeconds, LocalDateTime time) {
        return AccessToken.builder()
                .id(accessToken)
                .name(name)
                .timeout(remainingMilliSeconds/1000)
                .refreshTime(time)
                .build();
    }

    public void updateToken(String name, LocalDateTime refreshTime) {
        if (refreshTime.isAfter(this.refreshTime)) {
            this.name = name;
            this.refreshTime = refreshTime;
        }
    }
}
