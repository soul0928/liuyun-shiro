package com.liuyun.liuyunshiro.modules.service.impl;

import com.liuyun.liuyunshiro.modules.mapper.TestMapper;
import com.liuyun.liuyunshiro.modules.pojo.enyity.TestEntity;
import com.liuyun.liuyunshiro.modules.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: liuyun-shiro
 * @description:
 * @author: WangDong
 * @create: 2019-09-22 14:29
 **/
@Service("testService")
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public List<TestEntity> queryAll() {
        return testMapper.queryAll();
    }
}
