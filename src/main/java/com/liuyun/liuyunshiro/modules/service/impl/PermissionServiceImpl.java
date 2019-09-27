package com.liuyun.liuyunshiro.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuyun.liuyunshiro.common.util.bean.BeanUtils;
import com.liuyun.liuyunshiro.modules.mapper.PermissionMapper;
import com.liuyun.liuyunshiro.modules.pojo.dto.PermissionDTO;
import com.liuyun.liuyunshiro.modules.pojo.dto.RoleDTO;
import com.liuyun.liuyunshiro.modules.pojo.enyity.PermissionEntity;
import com.liuyun.liuyunshiro.modules.pojo.enyity.RoleEntity;
import com.liuyun.liuyunshiro.modules.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * @description 查询权限
     * @author 王栋
     * @date 2019/9/26 17:34
     * @param roleDTO
     * @return java.util.List<com.liuyun.liuyunshiro.modules.pojo.dto.PermissionDTO>
     **/
    @Override
    public List<PermissionDTO> queryAll(RoleDTO roleDTO) {
        if (roleDTO.getId() == null) {
            QueryWrapper<PermissionEntity> wrapper = new QueryWrapper<>();
            PermissionEntity permissionEntity = new PermissionEntity();
            permissionEntity.setDelFlag(RoleEntity.DEL_FLAG_NORMAL);
            wrapper.setEntity(permissionEntity);
            List<PermissionEntity> permissionEntitys = permissionMapper.selectList(wrapper);
            List<PermissionDTO> permissionDTOs = BeanUtils.copyList(permissionEntitys, PermissionDTO.class);
            return permissionDTOs;
        }
        List<PermissionEntity> roleEntities = permissionMapper.queryRolesByrole(roleDTO);
        List<PermissionDTO> permissionDTOs = BeanUtils.copyList(roleEntities, PermissionDTO.class);

        return permissionDTOs;
    }
}
