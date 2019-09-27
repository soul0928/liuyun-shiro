package com.liuyun.liuyunshiro.common.util.redis;

import com.liuyun.liuyunshiro.config.spring.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.Set;

/**
 * @ProjectName liuyun-shiro
 * @ClassName RedisUtils
 * @Description
 * @Author WangDong
 * @Date 2019/9/26 10:25
 * @Version 2.1.3
 **/
@Slf4j
public class RedisUtils {

    private static JedisPool jedisPool = (JedisPool) SpringBeanUtil.getBean("jedisPool");

    /**
     * @description set
     * @author 王栋
     * @date 2019/9/26 14:46
     * @param key
     * @param value
     * @return java.lang.String
     **/
    public static String set(String key, String value) {
        return set(key, value, -1);
    }

    /**
     * @description set
     * @author 王栋
     * @date 2019/9/26 14:39
     * @param key key
     * @param value value
     * @param timeOut 过期时间 单位：秒
     * @return java.lang.String
     **/
    public static String set(String key, String value, int timeOut) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Jedis resource = jedisPool.getResource();
            SetParams setParams = new SetParams();
            setParams.ex(timeOut);
            return jedis.set(key, value,setParams);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * @description get
     * @author 王栋
     * @date 2019/9/26 14:48
     * @param key
     * @return java.lang.String
     **/
    public static String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }

    }

    /**
     * @description  get
     * @author 王栋
     * @date 2019/9/26 14:48
     * @param key
     * @return byte[]
     **/
    public static byte[] get(byte[] key) {
        Jedis jedis = null;
        byte[] value = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {

            log.error(e.getMessage());
        }
        return value;
    }

    /**
     * @description set
     * @author 王栋
     * @date 2019/9/26 14:39
     * @param key key
     * @param value value
     * @param timeOut 过期时间 单位：秒
     * @return java.lang.String
     **/
    public static String set(byte[] key, byte[] value, int timeOut) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Jedis resource = jedisPool.getResource();
            SetParams setParams = new SetParams();
            setParams.ex(timeOut);
            return jedis.set(key, value, setParams);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * @description 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
     * @author 王栋
     * @date 2019/9/25 19:10
     * @param key  key
     * @param value value
     * @return java.lang.Long
     **/
    public static Long expire(String key, int value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.expire(key, value);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        }
    }

    public static boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * @description 删除指定前缀的key
     * @author 王栋
     * @date 2019/9/26 11:14
     * @param prefixShiroCache
     * @return void
     **/
    public static void delByPrefix(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key + ":*");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * @description
     * @author 王栋
     * @date 2019/9/26 14:48
     * @param key
     * @return java.util.Set<java.lang.String>
     **/
    public static Set<String> keys(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.keys(key + "*");
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * @description
     * @author 王栋
     * @date 2019/9/26 11:20
     * @param key
     * @return void
     **/
    public static Long del(String key) {
        Jedis jedis = null;
        byte[] value = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(key);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        }
    }

    /**
     * @description 获取过期时间
     * @author 王栋
     * @date 2019/9/26 14:49
     * @param key
     * @return Long  单位 ： 秒
     **/
    public static Long ttl(String key) {
        Jedis jedis = null;
        byte[] value = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.ttl(key);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        }
    }

    public static void main(String[] args) {
        System.out.println(set("aaa", "bbb"));

    }
}
