package com.liuyun.liuyunshiro.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuyun.liuyunshiro.common.constant.ShiroConstants;
import com.liuyun.liuyunshiro.common.util.bean.BeanUtils;
import com.liuyun.liuyunshiro.common.util.redis.RedisUtils;
import com.liuyun.liuyunshiro.modules.mapper.PermissionMapper;
import com.liuyun.liuyunshiro.modules.mapper.RoleMapper;
import com.liuyun.liuyunshiro.modules.mapper.UserMapper;
import com.liuyun.liuyunshiro.modules.pojo.dto.RolePermissionDTO;
import com.liuyun.liuyunshiro.modules.pojo.dto.UserDTO;
import com.liuyun.liuyunshiro.modules.pojo.enyity.PermissionEntity;
import com.liuyun.liuyunshiro.modules.pojo.enyity.RoleEntity;
import com.liuyun.liuyunshiro.modules.pojo.enyity.UserEntity;
import com.liuyun.liuyunshiro.modules.service.UserService;
import org.apache.commons.collections.SetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author WangDong
 * @since 2019-09-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * @description 根据用户查询其拥有角色及权限
     * @author 王栋
     * @date 2019/9/26 15:50
     * @param userDto
     * @return com.liuyun.liuyunshiro.modules.pojo.dto.RolePermissionDTO
     **/
    @Override
    public RolePermissionDTO queryRoleAndPermissionByUser(UserDTO userDto) {
        // 查询用户拥有角色
        List<RoleEntity> roleEntitys = roleMapper.queryRolesByUser(userDto);
        // 查询用户拥有权限
        List<PermissionEntity> permissionEntitys = permissionMapper.queryPermissionsByUser(userDto);
        RolePermissionDTO rolePermissionDTO = new RolePermissionDTO();
        List<String> roles = roleEntitys.stream().map(RoleEntity::getPerCode).collect(Collectors.toList());
        List<String> permissions = permissionEntitys.stream().map(PermissionEntity::getPerCode).collect(Collectors.toList());
        rolePermissionDTO.setRole(new HashSet<>(roles));
        rolePermissionDTO.setPermission(new HashSet<>(permissions));
        return rolePermissionDTO;
    }

    /**
     * @description 获取在线用户(查询Redis中的RefreshToken)
     * @author 王栋
     * @date 2019/9/27 10:05
     * @param
     * @return java.util.List<com.liuyun.liuyunshiro.modules.pojo.dto.UserDTO>
     **/
    @Override
    public List<UserDTO> online() {
        List<UserDTO> list = new ArrayList<>();
        Set<String> keys = RedisUtils.keys(ShiroConstants.PREFIX_SHIRO_REFRESH_TOKEN);
        for (String key : keys) {
            String[] str = key.split(":");
            UserEntity userEntity = new UserEntity();
            userEntity.setAccount(str[str.length - 1]);
            QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
            wrapper.setEntity(userEntity);
            UserEntity userinfo = userMapper.selectOne(wrapper);
            UserDTO userDTO = BeanUtils.copyProperties(userinfo, UserDTO.class);
            list.add(userDTO);
        }
        return list;
    }

}
