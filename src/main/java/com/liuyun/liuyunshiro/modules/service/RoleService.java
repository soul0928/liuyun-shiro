package com.liuyun.liuyunshiro.modules.service;

import com.liuyun.liuyunshiro.modules.pojo.dto.RoleDTO;
import com.liuyun.liuyunshiro.modules.pojo.dto.UserDTO;
import com.liuyun.liuyunshiro.modules.pojo.enyity.RoleEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author WangDong
 * @since 2019-09-23
 */
public interface RoleService extends IService<RoleEntity> {

    /**
     * @description 查询用户所有权限
     * @author 王栋
     * @date 2019/9/25 14:15
     * @param userDto
     * @return java.util.List<com.liuyun.liuyunshiro.modules.pojo.dto.RoleDTO>
     **/
    List<RoleDTO> queryRoleByUser(UserDTO userDto);
}
