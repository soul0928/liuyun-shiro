package com.liuyun.liuyunshiro.modules.service;

import com.liuyun.liuyunshiro.modules.pojo.dto.PermissionDTO;
import com.liuyun.liuyunshiro.modules.pojo.dto.RoleDTO;
import com.liuyun.liuyunshiro.modules.pojo.enyity.PermissionEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author WangDong
 * @since 2019-09-23
 */
public interface PermissionService extends IService<PermissionEntity> {

    /**
     * @description 查询权限
     * @author 王栋
     * @date 2019/9/26 17:33
     * @param roleDTO
     * @return java.util.List<com.liuyun.liuyunshiro.modules.pojo.dto.PermissionDTO>
     **/
    List<PermissionDTO> queryAll(RoleDTO roleDTO);
}
