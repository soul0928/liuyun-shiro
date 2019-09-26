package com.liuyun.liuyunshiro.modules.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuyun.liuyunshiro.common.constant.ShiroConstants;
import com.liuyun.liuyunshiro.common.exception.GlobalException;
import com.liuyun.liuyunshiro.common.result.Result;
import com.liuyun.liuyunshiro.common.util.id.IdUtil;
import com.liuyun.liuyunshiro.common.util.jwt.AesCipherUtils;
import com.liuyun.liuyunshiro.common.util.jwt.JwtUtils;
import com.liuyun.liuyunshiro.common.util.redis.RedisUtils;
import com.liuyun.liuyunshiro.modules.pojo.dto.UserDTO;
import com.liuyun.liuyunshiro.modules.pojo.enyity.UserEntity;
import com.liuyun.liuyunshiro.modules.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author WangDong
 * @since 2019-09-23
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * RefreshToken过期时间
     */
    @Value("${shiro.refresh.token.expire-time}")
    private String refreshTokenExpireTime;

    @Autowired
    private UserService userService;

    /**
     * @description 登录接口
     * @author 王栋
     * @date 2019/9/23 14:06
     * @param userDTO
     * @return com.liuyun.liuyunshiro.common.result.Result
     **/
    @PostMapping(value = "/login")
    public Result login(UserDTO userDTO, HttpServletResponse response) {
        log.info("用户登录请求参数为 [{}]", userDTO);
        UserEntity userEntity = new UserEntity();
        userEntity.setAccount(userDTO.getAccount());
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.setEntity(userEntity);
        UserEntity localUserEntity = userService.getOne(wrapper);
        if (localUserEntity == null) {
            return Result.fail("该帐号不存在");
        }
        // 密码进行AES解密
        String key = AesCipherUtils.deCrypto(localUserEntity.getPassword());
        // 因为密码加密是以帐号+密码的形式进行加密的，所以解密后的对比是帐号+密码
        if (key.equals(userDTO.getAccount() + userDTO.getPassword())) {
            // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            RedisUtils.set(ShiroConstants.PREFIX_SHIRO_REFRESH_TOKEN + userDTO.getAccount(), currentTimeMillis);
            RedisUtils.expire(ShiroConstants.PREFIX_SHIRO_REFRESH_TOKEN + userDTO.getAccount(), Integer.parseInt(refreshTokenExpireTime));
            // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
            String token = JwtUtils.sign(userDTO.getAccount(), currentTimeMillis);
            response.setHeader("Authorization", token);
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            return Result.success( "登录成功(Login Success.)", null);
        }

        return Result.fail("帐号或密码错误");
    }

    public static void main(String[] args) {
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        System.out.println("当前时间戳 " + currentTimeMillis);
        String token = JwtUtils.sign("demoData", currentTimeMillis);
        System.out.println("token" + token);

        String claim = JwtUtils.getClaim(token, "currentTimeMillis");
        System.out.println("取出的时间戳 " + claim);
    }

    @PostMapping(value = "/add")
    public Result add(UserDTO userDTO) {
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setAccount(userDTO.getAccount());
            QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
            wrapper.setEntity(userEntity);
            UserEntity localUserEntity = userService.getOne(wrapper);
            if (localUserEntity != null) {
                return Result.fail("当前账号已存在");
            }
            userEntity.setId(IdUtil.getNextId());
            String key = AesCipherUtils.enCrypto(userDTO.getAccount() + userDTO.getPassword());
            userEntity.setPassword(key);
            userEntity.beforeInsert();
            userService.save(userEntity);
            return Result.success();
        } catch (GlobalException e) {
            log.info(" [{}]", e.getMessage());
            return Result.fail("ADD 异常" + e.getMessage());
        }
    }
}
