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
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
            RedisUtils.set(ShiroConstants.PREFIX_SHIRO_REFRESH_TOKEN + userDTO.getAccount(), currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
            // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
            String token = JwtUtils.sign(userDTO.getAccount(), currentTimeMillis);
            response.setHeader("Authorization", token);
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            return Result.success( "登录成功(Login Success.)", null);
        }

        return Result.fail("帐号或密码错误");
    }

    /**
     * @description 新增用户
     * @author 王栋
     * @date 2019/9/27 10:01
     * @param userDTO
     * @return com.liuyun.liuyunshiro.common.result.Result
     **/
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
            log.info("新增用户，发生错误 [{}]", e.getMessage());
            return Result.fail("ADD 异常" + e.getMessage());
        }
    }

    /**
     * @description 获取在线用户(查询Redis中的RefreshToken)
     * @author 王栋
     * @date 2019/9/27 10:04
     * @param
     * @return com.liuyun.liuyunshiro.common.result.Result
     **/
    @GetMapping("/online")
    public Result online() {
        try {
            List<UserDTO> list = userService.online();
             return Result.success(list);
        } catch (GlobalException e) {
            log.info("获取在线用户，发生错误  [{}]", e.getMessage());
            return Result.fail(e.getMessage());
        }
    }

    /**
     * @description 更新用户
     * @author 王栋
     * @date 2019/9/27 10:26
     * @param userDTO
     * @return com.liuyun.liuyunshiro.common.result.Result
     **/
    @PutMapping
    @RequiresPermissions(logical = Logical.AND, value = {"user:edit"})
    public Result update(UserDTO userDTO) {

        //TODO

        /**
         * 在退出登录 / 修改密码时怎样实现JWT Token失效？
         *
         *
         * 时间戳 校验
         *
         *
         **/

        return Result.success();
    }

    /**
     * @description 删除用户
     * @author 王栋
     * @date 2019/9/27 10:27
     * @param id
     * @return ResponseBean
     **/
    @DeleteMapping("/{id}")
    @RequiresPermissions(logical = Logical.AND, value = {"user:edit"})
    public Result delete(@PathVariable("id") Integer id) {



        return Result.success();
    }

    /**
     * @description 剔除在线用户
     * @author 王栋
     * @date 2019/9/27 10:27
     * @param id
     * @return ResponseBean
     **/
    @DeleteMapping("/online/{id}")
    @RequiresPermissions(logical = Logical.AND, value = {"user:edit"})
    public Result deleteOnline(@PathVariable("id") Integer id) {

        return Result.success();
    }
}
