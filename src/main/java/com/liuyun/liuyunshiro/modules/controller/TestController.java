package com.liuyun.liuyunshiro.modules.controller;

import com.liuyun.liuyunshiro.common.result.Result;
import com.liuyun.liuyunshiro.modules.pojo.enyity.TestEntity;
import com.liuyun.liuyunshiro.modules.service.TestService;
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

}
