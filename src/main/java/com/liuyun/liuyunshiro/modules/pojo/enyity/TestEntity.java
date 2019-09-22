package com.liuyun.liuyunshiro.modules.pojo.enyity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * @program: liuyun-shiro
 * @description: 测试
 * @author: WangDong
 * @create: 2019-09-22 14:27
 **/
@Data
@Alias("TestEntity")
public class TestEntity {

    private Long id;
    private String name;
    private Date birthday;

}
