package com.liuyun.liuyunshiro.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @ProjectName liuyun-shiro
 * @ClassName LiuYunJwtToken
 * @Description JwtToken
 * @Author WangDong
 * @Date 2019/9/24 19:31
 * @Version 2.1.3
 **/
public class LiuYunJwtToken implements AuthenticationToken {

    /**
     * Token
     */
    private String token;

    public LiuYunJwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
