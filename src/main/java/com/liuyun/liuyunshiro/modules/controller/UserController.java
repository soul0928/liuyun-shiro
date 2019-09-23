package com.liuyun.liuyunshiro.modules.controller;


import com.liuyun.liuyunshiro.common.result.Result;
import com.liuyun.liuyunshiro.modules.pojo.enyity.UserEntity;
import com.liuyun.liuyunshiro.modules.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author WangDong
 * @since 2019-09-23
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("save")
    public Result save(Long id) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setNamr("aaa");
        userEntity.setPhone("18811112222");
        userEntity.setUsername("aaaa");
        userEntity.setPassword("aaaa");
        userEntity.beforeInsert();
        userService.save(userEntity);
        return Result.success();
    }

    @RequestMapping("query")
    public Result query() {
        List<UserEntity> list = userService.list();
        return Result.success(list);
    }
}
