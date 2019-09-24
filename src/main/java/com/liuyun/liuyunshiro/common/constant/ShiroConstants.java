package com.liuyun.liuyunshiro.common.constant;

/**
 * @program: liuyun-shiro
 * @description: 缓存常量
 * @author: WangDong
 * @create: 2019-09-22 13:08
 **/
public class ShiroConstants {

    /**
     * JWT-account:
     */
    public final static String ACCOUNT = "account";

    /**
     * redis-key-前缀-shiro:cache:
     */
    public final static String PREFIX_SHIRO_CACHE = "liuyun:shiro:cache:";

    /**
     * redis-key-前缀-shiro:refresh_token:
     */
    public final static String PREFIX_SHIRO_REFRESH_TOKEN = "liuyun:shiro:refresh_token:";
}
