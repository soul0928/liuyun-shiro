package com.liuyun.liuyunshiro.config.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liuyun.liuyunshiro.common.util.properties.PropertiesUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.sql.SQLOutput;
import java.time.Duration;
import java.util.HashSet;

/**
 * @ProjectName liuyun-shiro
 * @ClassName LiuYunRedisConfig
 * @Description
 * @Author WangDong
 * @Date 2019/9/23 19:24
 * @Version 2.1.3
 **/
@Configuration
public class LiuYunRedisConfig extends CachingConfigurerSupport {

    /**
     * Redis服务器地址
     **/
    private static String host = (String) PropertiesUtils.getYml("spring.redis.host");

    /**
     * Redis服务器连接端口
     **/
    private String port = (String) PropertiesUtils.getYml("spring.redis.port");
    /**
     * Redis数据库索引（默认为0）
     **/
    private static String database = (String) PropertiesUtils.getYml("spring.redis.database");
    /**
     * Redis服务器连接密码（默认为空）
     **/
    private static String password = (String) PropertiesUtils.getYml("spring.redis.password");
    /**
     * 连接超时时间（毫秒）
     **/
    private static String timeout = (String) PropertiesUtils.getYml("spring.redis.timeout");
    /**
     * 连接池最大连接数（使用负值表示没有限制）
     **/
    private static String maxTotal = (String) PropertiesUtils.getYml("spring.redis.lettuce.pool.max-active");
    /**
     * 连接池最大阻塞等待时间（使用负值表示没有限制）
     **/
    private static String maxWait = (String) PropertiesUtils.getYml("spring.redis.lettuce.pool.max-wait");
    /**
     * 连接池中的最大空闲连接
     **/
    private static String maxIdle = (String) PropertiesUtils.getYml("spring.redis.lettuce.pool.max-idle");
    /**
     * 连接池中的最小空闲连接
     **/
    private static String minIdle = (String) PropertiesUtils.getYml("spring.redis.lettuce.pool.min-idle");
    /**
     * 关闭超时时间
     **/
    private static String shutdown = (String) PropertiesUtils.getYml("spring.redis.lettuce.shutdown-timeout");

    /**
     * 自定义缓存key的生成策略。
     * 默认的生成策略是看不懂的(乱码内容)
     * 通过Spring 的依赖注入特性进行自定义的配置注入并且此类是一个配置类可以更多程度的自定义配置
     *
     * @return
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    /**
     * 缓存配置管理器
     */
    @Bean
    public CacheManager cacheManager(LettuceConnectionFactory factory) {
        //以锁写入的方式创建RedisCacheWriter对象
        RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(factory);
        /**
         * 设置CacheManager的Value序列化方式为JdkSerializationRedisSerializer,
         * 但其实RedisCacheConfiguration默认就是使用
         * StringRedisSerializer序列化key，
         * JdkSerializationRedisSerializer序列化value,
         * 所以以下注释代码就是默认实现，没必要写，直接注释掉
         *
         * RedisSerializationContext.SerializationPair pair = RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer(this.getClass().getClassLoader()));
         * RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
         *
         **/

        //创建默认缓存配置对象
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        RedisCacheManager cacheManager = new RedisCacheManager(writer, config);
        return cacheManager;
    }

    /**
     * @Description 获取缓存操作助手对象
     * @Author 王栋
     * @Date 2019/4/20 23:37
     * @param:
     * @return org.springframework.data.redis.core.RedisTemplate<java.lang.String,java.lang.String>
     **/
    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        //创建Redis缓存操作助手RedisTemplate对象
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(getConnectionFactory());

        /**
         * 以下代码为将RedisTemplate的Value序列化方式
         * 由JdkSerializationRedisSerializer更换为Jackson2JsonRedisSerializer、
         * JDK自带的序列化工具出来的二进制过长，再分布式环境，特别是跨语言的环境
         *
         * Jackson2JsonRedisSerializer
         * 此种序列化方式结果清晰、容易阅读、存储字节少、速度快，所以推荐更换
         **/
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);

        //RedisTemplate对象需要指明Key序列化方式，如果声明StringRedisTemplate对象则不需要
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        //是否启用事务
        //template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * @Description 获取缓存连接
     * @Author 王栋
     * @Date 2019/4/20 23:37
     * @param:
     * @return org.springframework.data.redis.connection.RedisConnectionFactory
     **/
    @Bean
    public LettuceConnectionFactory getConnectionFactory() {
        //单机模式
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        //哨兵模式
        //RedisSentinelConfiguration configuration = new RedisSentinelConfiguration();
        //集群模式
        //RedisClusterConfiguration configuration = new RedisClusterConfiguration();
        configuration.setHostName(host);
        configuration.setPort(Integer.parseInt(port));
        // 默认 0
        //configuration.setMaxRedirects();
        configuration.setPassword(RedisPassword.of(password));

        LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration, getPoolConfig());
        //factory.setShareNativeConnection(false);//是否允许多个线程操作共用同一个缓存连接，默认true，false时每个操作都将开辟新的连接
        return factory;
    }

    /**
     * @Description 获取缓存连接池
     * @Author 王栋
     * @Date 2019/4/20 23:36
     * @param:
     * @return org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration
     **/
    @Bean
    public LettucePoolingClientConfiguration getPoolConfig() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(Integer.parseInt(maxTotal));
        config.setMaxWaitMillis(Long.parseLong(maxWait));
        config.setMaxIdle(Integer.parseInt(maxIdle));
        config.setMinIdle(Integer.parseInt(minIdle));
        LettucePoolingClientConfiguration pool = LettucePoolingClientConfiguration.builder()
                .poolConfig(config)
                .commandTimeout(Duration.ofMillis(Long.parseLong(timeout)))
                .shutdownTimeout(Duration.ofMillis(Long.parseLong(shutdown)))
                .build();
        return pool;
    }

    public static void main(String[] args) {
        System.out.println(host);

    }

}
