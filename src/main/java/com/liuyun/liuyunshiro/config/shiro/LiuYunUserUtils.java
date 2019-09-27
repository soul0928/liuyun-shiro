package com.liuyun.liuyunshiro.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuyun.liuyunshiro.common.constant.ShiroConstants;
import com.liuyun.liuyunshiro.common.exception.GlobalException;
import com.liuyun.liuyunshiro.common.util.bean.BeanUtils;
import com.liuyun.liuyunshiro.common.util.jwt.JwtUtils;
import com.liuyun.liuyunshiro.modules.pojo.dto.UserDTO;
import com.liuyun.liuyunshiro.modules.pojo.enyity.UserEntity;
import com.liuyun.liuyunshiro.modules.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ProjectName liuyun-shiro
 * @ClassName LiuYunUserUtils
 * @Description 获取当前登录用户工具类
 * @Author WangDong
 * @Date 2019/9/27 10:16
 * @Version 2.1.3
 **/
@Component
public class LiuYunUserUtils {

    private final UserService userService;

    @Autowired
    public LiuYunUserUtils(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获取当前登录用户
     * @param
     * @return com.wang.model.UserDto
     * @author Wang926454
     * @date 2019/3/15 11:48
     */
    public UserDTO getUser() {
        String token = getToken();
        // 解密获得Account
        String account = JwtUtils.getClaim(token, ShiroConstants.ACCOUNT);
        UserEntity userEntity = new UserEntity();
        userEntity.setAccount(account);
        userEntity.setDelFlag(UserEntity.DEL_FLAG_NORMAL);
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.setEntity(userEntity);
        UserEntity one = userService.getOne(wrapper);
        if (one == null) {
            throw new GlobalException("该帐号不存在(The account does not exist.)");
        }
        return BeanUtils.copyProperties(one, UserDTO.class);
    }

    /**
     * 获取当前登录用户Token
     * @param
     * @return com.wang.model.UserDto
     * @author Wang926454
     * @date 2019/3/15 11:48
     */
    public String getToken() {
        return SecurityUtils.getSubject().getPrincipal().toString();
    }
}
