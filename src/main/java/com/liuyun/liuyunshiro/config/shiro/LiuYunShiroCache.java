package com.liuyun.liuyunshiro.config.shiro;

import com.liuyun.liuyunshiro.common.constant.ShiroConstants;
import com.liuyun.liuyunshiro.common.util.json.GsonConvertUtils;
import com.liuyun.liuyunshiro.common.util.jwt.JwtUtils;
import com.liuyun.liuyunshiro.common.util.jwt.SerializableUtils;
import com.liuyun.liuyunshiro.common.util.properties.PropertiesUtils;
import com.liuyun.liuyunshiro.common.util.redis.RedisUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName liuyun-shiro
 * @ClassName LiuYunCache
 * @Description
 * @Author WangDong
 * @Date 2019/9/24 15:51
 * @Version 2.1.3
 **/
public class LiuYunShiroCache<K,V> implements Cache<K,V> {

    private static String cacheExpireTime = (String) PropertiesUtils.getYml("shiro.cache.expire-time");
    /**
     * @description 缓存的key名称获取为 liuyun:shiro:cache:account
     * @author 王栋
     * @date 2019/9/24 16:37
     * @param key
     * @return java.lang.String
     **/
    private String getKey(Object key) {
        return ShiroConstants.PREFIX_SHIRO_CACHE + JwtUtils.getClaim(key.toString(), ShiroConstants.ACCOUNT);
    }

    /**
     * @description 获取缓存
     * @author 王栋
     * @date 2019/9/25 13:40
     * @param key
     * @return java.lang.Object
     **/
    @Override
    public Object get(Object key) throws CacheException {
        if(!RedisUtils.exists(getKey(key))){
            return null;
        }
        String s = RedisUtils.get(getKey(key));
        LiuYunSimpleAuthorizationInfo liuYunSimpleAuthorizationInfo = GsonConvertUtils.toBean(s, LiuYunSimpleAuthorizationInfo.class);
        return liuYunSimpleAuthorizationInfo;
    }

    /**
     * @description 保存缓存
     * @author 王栋
     * @date 2019/9/25 13:40
     * @param key
     * @param value
     * @return java.lang.Object
     **/
    @Override
    public Object put(Object key, Object value) throws CacheException {
        // 读取配置文件，获取Redis的Shiro缓存过期时间
        String cachekey = getKey(key);
        int aLong = Integer.parseInt(cacheExpireTime);
        //byte[] serializable = SerializableUtils.serializable(value);
        String s = GsonConvertUtils.toJson(value);
        return RedisUtils.set(cachekey, s, aLong);
    }

    /**
     * @description 移除缓存
     * @author 王栋
     * @date 2019/9/25 13:40
     * @param key
     * @return java.lang.Object
     **/
    @Override
    public Object remove(Object key) throws CacheException {
        if(!RedisUtils.exists(getKey(key))){
            return null;
        }
        RedisUtils.del(getKey(key));
        return null;
    }

    /**
     * @description 清空所有缓存
     * @author 王栋
     * @date 2019/9/25 13:40
     * @param
     * @return void
     **/
    @Override
    public void clear() throws CacheException {
        RedisUtils.delByPrefix(ShiroConstants.PREFIX_SHIRO_CACHE);
    }

    /**
     * @description 缓存的个数
     * @author 王栋
     * @date 2019/9/25 13:40
     * @param
     * @return int
     **/
    @Override
    public int size() {
        return RedisUtils.keys(ShiroConstants.PREFIX_SHIRO_CACHE).size();
    }

    /**
     * @description 获取所有的key
     * @author 王栋
     * @date 2019/9/25 13:40
     * @param
     * @return java.util.Set
     **/
    @Override
    public Set keys() {
        return RedisUtils.keys(ShiroConstants.PREFIX_SHIRO_CACHE);
    }

    /**
     * @description 获取所有的value
     * @author 王栋
     * @date 2019/9/25 13:40
     * @param
     * @return java.util.Collection
     **/
    @Override
    public Collection values() {
        List<Object> values = new ArrayList<>();
        for (Object key : keys()) {
            values.add(get(key));
        }
        return values;
    }

    public static void main(String[] args) {
        System.out.println(Long.parseLong(cacheExpireTime));
    }
}
