package com.liuyun.liuyunshiro.modules.mapper;

import com.liuyun.liuyunshiro.modules.pojo.enyity.TestEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: liuyun-shiro
 * @description:
 * @author: WangDong
 * @create: 2019-09-22 14:29
 **/
@Mapper
public interface TestMapper {

    List<TestEntity> queryAll();
}
