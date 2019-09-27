package com.liuyun.liuyunshiro.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuyun.liuyunshiro.common.util.bean.BeanUtils;
import com.liuyun.liuyunshiro.modules.pojo.dto.RoleDTO;
import com.liuyun.liuyunshiro.modules.pojo.dto.RolePermissionDTO;
import com.liuyun.liuyunshiro.modules.pojo.dto.UserDTO;
import com.liuyun.liuyunshiro.modules.pojo.enyity.RoleEntity;
import com.liuyun.liuyunshiro.modules.mapper.RoleMapper;
import com.liuyun.liuyunshiro.modules.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author WangDong
 * @since 2019-09-23
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * @description 查询角色
     * @author 王栋
     * @date 2019/9/26 16:51
     * @param userDTO
     * @return java.util.List<com.liuyun.liuyunshiro.modules.pojo.dto.RoleDTO>
     **/
    @Override
    public List<RoleDTO> queryAll(UserDTO userDTO) {
        if (userDTO.getId() == null) {
            QueryWrapper<RoleEntity> wrapper = new QueryWrapper<>();
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setDelFlag(RoleEntity.DEL_FLAG_NORMAL);
            wrapper.setEntity(roleEntity);
            List<RoleEntity> roleEntities = roleMapper.selectList(wrapper);
            List<RoleDTO> roleDTOS = BeanUtils.copyList(roleEntities, RoleDTO.class);
            return roleDTOS;
        }
        List<RoleEntity> roleEntities = roleMapper.queryRolesByUser(userDTO);
        List<RoleDTO> roleDTOS = BeanUtils.copyList(roleEntities, RoleDTO.class);
        return roleDTOS;
    }
}
