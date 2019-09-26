package com.liuyun.liuyunshiro.config.redis;

import com.liuyun.liuyunshiro.common.util.properties.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @ProjectName liuyun-shiro
 * @ClassName LiuYunRedisConfig
 * @Description
 * @Author WangDong
 * @Date 2019/9/23 19:24
 * @Version 2.1.3
 **/
@Slf4j
@Configuration
public class LiuYunRedisConfig extends CachingConfigurerSupport {

    private static String host = PropertiesUtils.getYml("spring.redis.host");

    private String port = PropertiesUtils.getYml("spring.redis.port");

    private String timeout = PropertiesUtils.getYml("spring.redis.timeout");

    private String maxIdle = PropertiesUtils.getYml("spring.redis.jedis.pool.max-idle");

    private String maxActive = PropertiesUtils.getYml("spring.redis.jedis.pool.max-active");

    private String minIdle = PropertiesUtils.getYml("spring.redis.jedis.pool.min-idle");

    private String maxWaitMillis = PropertiesUtils.getYml("spring.redis.jedis.pool.max-wait");

    private String password = PropertiesUtils.getYml("spring.redis.password");

    private String  blockWhenExhausted = PropertiesUtils.getYml("spring.redis.block-when-exhausted");

    @Bean
    public JedisPool jedisPool() {
        try {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle(Integer.parseInt(maxIdle));
            jedisPoolConfig.setMaxWaitMillis(Integer.valueOf(minIdle));
            jedisPoolConfig.setMaxTotal(Integer.parseInt(maxActive));
            jedisPoolConfig.setMinIdle(Integer.valueOf(minIdle));
            // JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
            String pwd = StringUtils.isBlank(password) ? null : password;
            JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, Integer.parseInt(port), Integer.valueOf(timeout), pwd);
            log.info("初始化Redis连接池JedisPool成功,地址: [{}]", host + ":" + port);
            return jedisPool;
        } catch (Exception e) {
            log.error("初始化Redis连接池JedisPool异常:" + e.getMessage());
        }
        return null;
    }
}
