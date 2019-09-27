package com.liuyun.liuyunshiro.modules.controller;

import com.liuyun.liuyunshiro.common.result.Result;
import com.liuyun.liuyunshiro.modules.pojo.enyity.TestEntity;
import com.liuyun.liuyunshiro.modules.service.TestService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: liuyun-shiro
 * @description: test
 * @author: WangDong
 * @create: 2019-09-22 14:28
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/queryAll")
    public Result queryAll() {
        List<TestEntity> list = testService.queryAll();
        return Result.success(list);
    }

    @RequestMapping("/queryAll2")
    @RequiresPermissions(value = {"test.query"})
    public Result queryAll2() {
        List<TestEntity> list = testService.queryAll();
        return Result.success(list);
    }

    @RequestMapping("/queryAll3")
    @RequiresPermissions(value = {"test.delete"})
    public Result del() {
        return Result.success();
    }

    @RequestMapping("/queryAll4")
    @RequiresRoles(value = {"role.superadmin"})
    public Result queryAll3() {
        List<TestEntity> list = testService.queryAll();
        return Result.success(list);
    }

}
