package com.liuyun.liuyunshiro.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuyun.liuyunshiro.modules.pojo.dto.RolePermissionDTO;
import com.liuyun.liuyunshiro.modules.pojo.dto.UserDTO;
import com.liuyun.liuyunshiro.modules.pojo.enyity.UserEntity;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author WangDong
 * @since 2019-09-23
 */
public interface UserService extends IService<UserEntity> {

    /**
     * @description 根据用户查询其拥有角色及权限
     * @author 王栋
     * @date 2019/9/26 15:50
     * @param userDto
     * @return com.liuyun.liuyunshiro.modules.pojo.dto.RolePermissionDTO
     **/
    RolePermissionDTO queryRoleAndPermissionByUser(UserDTO userDto);

}
