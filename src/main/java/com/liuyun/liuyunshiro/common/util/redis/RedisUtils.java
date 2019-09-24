package com.liuyun.liuyunshiro.common.util.redis;

import com.liuyun.liuyunshiro.common.exception.GlobalException;
import com.liuyun.liuyunshiro.config.spring.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName liuyun-shiro
 * @ClassName RedisUtils
 * @Description
 * @Author WangDong
 * @Date 2019/9/23 20:36
 * @Version 2.1.3
 **/
@Slf4j
public class RedisUtils {

    private static RedisTemplate<String, String> template = (RedisTemplate<String, String>) SpringBeanUtil.getBean("redisTemplate");

    public static void set(String key, String value) {
        template.opsForValue().set(key, value);
    }

    public static void set(String key, String value, long timeout) {
        template.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    public static String set(String key, String value, long timeout, TimeUnit unit) {
        try {
            template.opsForValue().set(key, value, timeout, unit);
            return "ok";
        } catch (GlobalException e) {
            return "";
        }
    }

    public static String get(String key) {
        return template.opsForValue().get(key);
    }

    public static Boolean del(String key) {
        return template.delete(key);
    }

    public static Boolean exists(String key) {
        return template.hasKey(key);
    }


    /**
     * 根据key前缀批量删除缓存
     * @param prefix 前缀
     * @return
     */
    public static Boolean delByPrefix(String prefix) {
        return template.delete(prefix + "*");
    }

    /**
     * 根据前缀获取所有key
     * @param prefix
     * @return
     */
    public static Set<String> keys(String prefix) {
        return template.keys(prefix + "*");
    }


}
