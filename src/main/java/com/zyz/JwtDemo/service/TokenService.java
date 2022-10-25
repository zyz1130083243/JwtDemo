package com.zyz.JwtDemo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zyz.JwtDemo.constants.RedisEnum;
import com.zyz.JwtDemo.constants.StateCode;
import com.zyz.JwtDemo.exception.LoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Token服务
 */
@Service
public class TokenService {

    @Value("{jwt.secret}")
    private String secret;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 生成Token
     * @param userId
     * @return
     */
    public String buildToken(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new LoginException(StateCode.USER_NOT_FOUND);
        }

        String key = RedisEnum.TOKEN_VERSION.buildKey(userId);
        Long value = redisTemplate.opsForValue().increment(key);
        redisTemplate.expire(key, RedisEnum.TOKEN_VERSION.getExpiredTime(), TimeUnit.SECONDS);

        Date now = new Date();
        return JWT.create()
                .withAudience(userId)
                .withJWTId(String.valueOf(value))
                .withIssuedAt(now)
//                .withExpiresAt()
                .sign(Algorithm.HMAC256(userId + secret));
    }

    /**
     * 校验Token
     * @param token
     * @return
     */
    public String verifyToken(String token) {
        DecodedJWT decoded = JWT.decode(token);

        String userId = decoded.getAudience().get(0);
        String tokenVersion = decoded.getClaim("jti").asString();

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(userId + secret)).build();
        verifier.verify(token);

        // 从redis校验token version
        String key = RedisEnum.TOKEN_VERSION.buildKey(userId);
        String value = redisTemplate.opsForValue().get(key);

        // 登录已过期
        if (StringUtils.isEmpty(value) || !tokenVersion.equals(value)) {
            throw new LoginException(StateCode.TOKEN_INVALID);
        }

        return userId;
    }

    /**
     * 清除token
     * @param userId
     */
    public void clearToken(String userId) {
        String key = RedisEnum.TOKEN_VERSION.buildKey(userId);
        redisTemplate.opsForValue().increment(key);
        redisTemplate.expire(key, RedisEnum.TOKEN_VERSION.getExpiredTime(), TimeUnit.SECONDS);
    }

}
