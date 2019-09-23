package com.liuyun.liuyunshiro.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuyun.liuyunshiro.modules.mapper.RolePermissionMapper;
import com.liuyun.liuyunshiro.modules.pojo.enyity.RolePermissionEntity;
import com.liuyun.liuyunshiro.modules.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色资源表 服务实现类
 * </p>
 *
 * @author WangDong
 * @since 2019-09-23
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermissionEntity> implements RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

}
