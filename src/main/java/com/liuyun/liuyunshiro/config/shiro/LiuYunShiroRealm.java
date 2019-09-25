package com.liuyun.liuyunshiro.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuyun.liuyunshiro.common.constant.ShiroConstants;
import com.liuyun.liuyunshiro.common.util.jwt.JwtUtils;
import com.liuyun.liuyunshiro.common.util.redis.RedisUtils;
import com.liuyun.liuyunshiro.modules.pojo.dto.RoleDTO;
import com.liuyun.liuyunshiro.modules.pojo.dto.UserDTO;
import com.liuyun.liuyunshiro.modules.pojo.enyity.UserEntity;
import com.liuyun.liuyunshiro.modules.service.RoleService;
import com.liuyun.liuyunshiro.modules.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private RoleService roleService;

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
     * @description 授权器
     * @author 王栋
     * @date 2019/9/25 13:43
     * @param principals
     * @return org.apache.shiro.authz.AuthorizationInfo
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        LiuYunSimpleAuthorizationInfo liuYunSimpleAuthorizationInfo = new LiuYunSimpleAuthorizationInfo();

        /*SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String account = JwtUtils.getClaim(principals.toString(), ShiroConstants.ACCOUNT);
        UserDTO userDto = new UserDTO();
        userDto.setAccount(account);
        // 查询用户角色
        List<RoleDTO> roleDtos = roleService.queryRoleByUser(userDto);

        simpleAuthorizationInfo.addRole("user:view");
        Set<String> set = new HashSet<>();
        set.add("user:view");
        simpleAuthorizationInfo.setStringPermissions(set);*/
        Set<String> set = new HashSet<>();
        set.add("user:view");
        liuYunSimpleAuthorizationInfo.addStringPermissions(set);

        return liuYunSimpleAuthorizationInfo;
    }

    /**
     * @description 认证器
     * @author 王栋
     * @date 2019/9/25 13:43
     * @param authenticationToken
     * @return org.apache.shiro.authc.AuthenticationInfo
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        log.info("shiro 登录认证器获取Token为 [{}]", token);
        // 解密获得account，用于和数据库进行对比
        String account = JwtUtils.getClaim(token, ShiroConstants.ACCOUNT);
        log.info("shiro 登录认证器获取Account为 [{}]", account);
        if (StringUtils.isBlank(account)) {
            throw new AuthenticationException("该帐号不存在");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setAccount(account);
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.setEntity(userEntity);
        UserEntity localUserEntity = userService.getOne(wrapper);
        if (localUserEntity == null) {
            throw new AuthenticationException("该帐号不存在");
        }
        // 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
        if (JwtUtils.verify(token) && RedisUtils.exists(ShiroConstants.PREFIX_SHIRO_REFRESH_TOKEN + account)) {
            // 获取RefreshToken的时间戳
            String currentTimeMillisRedis = RedisUtils.get(ShiroConstants.PREFIX_SHIRO_REFRESH_TOKEN + account);
            // 获取AccessToken时间戳，与RefreshToken的时间戳对比
            if (JwtUtils.getClaim(token, ShiroConstants.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
                return new SimpleAuthenticationInfo(token, token, "liuYunShiroRealm");
            }
        }
        throw new AuthenticationException("Token已过期");
    }
}
