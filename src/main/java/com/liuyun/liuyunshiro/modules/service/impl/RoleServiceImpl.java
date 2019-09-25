package com.liuyun.liuyunshiro.modules.service.impl;

import com.liuyun.liuyunshiro.modules.pojo.dto.RoleDTO;
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
     * @description 查询用户所有权限
     * @author 王栋
     * @date 2019/9/25 14:17
     * @param userDto
     * @return java.util.List<com.liuyun.liuyunshiro.modules.pojo.dto.RoleDTO>
     **/
    @Override
    public List<RoleDTO> queryRoleByUser(UserDTO userDto) {
        //List<RoleEntity> roleEntitys = roleMapper.queryRoleByUser(userDto);
        return null;
    }
}
