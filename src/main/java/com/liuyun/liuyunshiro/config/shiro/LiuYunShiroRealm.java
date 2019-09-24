package com.liuyun.liuyunshiro.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuyun.liuyunshiro.common.constant.ShiroConstants;
import com.liuyun.liuyunshiro.common.result.Result;
import com.liuyun.liuyunshiro.common.util.jwt.JwtUtils;
import com.liuyun.liuyunshiro.modules.pojo.enyity.UserEntity;
import com.liuyun.liuyunshiro.modules.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ProjectName liuyun-shiro
 * @ClassName LiuYunRealm
 * @Description 自定义Realm
 * @Author WangDong
 * @Date 2019/9/23 13:41
 * @Version 2.1.3
 **/
@Slf4j
@Component
public class LiuYunShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 基于 token 必须重写此方法
     * 用于检测是否支持此Token, 默认的采用了return false;
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof LiuYunJwtToken;
    }

    /**
     * 授权器
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 认证器
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        log.info("shiro 登录认证器获取Token为 [{}]", token);
        // 解密获得account，用于和数据库进行对比
        String account = JwtUtils.getClaim(token, ShiroConstants.ACCOUNT);
        log.info("shiro 登录认证器获取Account为 [{}]", account);


        return null;
    }

    public static void main(String[] args) {
        //算出盐值
        String credentials="admin";
        String salt="admin";
        String hashAlgorithmName="MD5";
        int hashIterations=1024;
        SimpleHash simpleHash = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        System.out.println(simpleHash);
    }
}
