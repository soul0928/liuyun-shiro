package com.liuyun.liuyunshiro.modules.service.impl;

import com.liuyun.liuyunshiro.modules.pojo.enyity.UserRoleEntity;
import com.liuyun.liuyunshiro.modules.mapper.UserRoleMapper;
import com.liuyun.liuyunshiro.modules.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author WangDong
 * @since 2019-09-23
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleEntity> implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

}
