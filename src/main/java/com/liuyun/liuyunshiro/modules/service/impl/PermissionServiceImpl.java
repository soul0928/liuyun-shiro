package com.liuyun.liuyunshiro.modules.service.impl;

import com.liuyun.liuyunshiro.modules.pojo.enyity.PermissionEntity;
import com.liuyun.liuyunshiro.modules.mapper.PermissionMapper;
import com.liuyun.liuyunshiro.modules.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author WangDong
 * @since 2019-09-23
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionEntity> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

}
