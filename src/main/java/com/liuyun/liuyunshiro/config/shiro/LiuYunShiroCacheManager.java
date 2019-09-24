package com.liuyun.liuyunshiro.config.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * @ProjectName liuyun-shiro
 * @ClassName LiuYunCacheManager
 * @Description 重写Shiro缓存管理器
 * @Author WangDong
 * @Date 2019/9/24 15:50
 * @Version 2.1.3
 **/
public class LiuYunShiroCacheManager implements CacheManager {


    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new LiuYunShiroCache<>();
    }
}
