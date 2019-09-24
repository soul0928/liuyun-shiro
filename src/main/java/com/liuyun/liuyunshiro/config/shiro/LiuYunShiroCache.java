package com.liuyun.liuyunshiro.config.shiro;

import com.liuyun.liuyunshiro.common.constant.ShiroConstants;
import com.liuyun.liuyunshiro.common.util.jwt.JwtUtils;
import com.liuyun.liuyunshiro.common.util.properties.PropertiesUtils;
import com.liuyun.liuyunshiro.common.util.redis.RedisUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

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
     * 获取缓存
     */
    @Override
    public Object get(Object key) throws CacheException {
        if(!RedisUtils.exists(getKey(key))){
            return null;
        }
        return RedisUtils.get(getKey(key));
    }

    /**
     * 保存缓存
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {
        // 读取配置文件，获取Redis的Shiro缓存过期时间
        Long time = (Long) PropertiesUtils.getYml("shiro.cache.expire-time");
        return RedisUtils.set(getKey(key), (String) value, time, TimeUnit.SECONDS);
    }

    /**
     * 移除缓存
     */
    @Override
    public Object remove(Object key) throws CacheException {
        if(!RedisUtils.exists(getKey(key))){
            return null;
        }
        RedisUtils.del(getKey(key));
        return null;
    }

    /**
     * 清空所有缓存
     */
    @Override
    public void clear() throws CacheException {
        RedisUtils.delByPrefix(ShiroConstants.PREFIX_SHIRO_CACHE);
    }

    /**
     * 缓存的个数
     */
    @Override
    public int size() {
        return RedisUtils.keys(ShiroConstants.PREFIX_SHIRO_CACHE).size();
    }

    /**
     * 获取所有的key
     */
    @Override
    public Set keys() {
        return RedisUtils.keys(ShiroConstants.PREFIX_SHIRO_CACHE);
    }

    /**
     * 获取所有的value
     */
    @Override
    public Collection values() {
        List<Object> values = new ArrayList<>();
        for (Object key : keys()) {
            values.add(get(key));
        }
        return values;
    }
}
