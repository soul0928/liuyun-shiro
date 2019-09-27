package com.liuyun.liuyunshiro.modules.mapper;

import com.liuyun.liuyunshiro.modules.pojo.dto.RoleDTO;
import com.liuyun.liuyunshiro.modules.pojo.dto.UserDTO;
import com.liuyun.liuyunshiro.modules.pojo.enyity.PermissionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author WangDong
 * @since 2019-09-23
 */
@Mapper
public interface PermissionMapper extends BaseMapper<PermissionEntity> {

    /**
     * @description 查询用户拥有权限
     * @author 王栋
     * @date 2019/9/26 16:09
     * @param userDto
     * @return java.util.List<java.lang.String>
     **/
    List<PermissionEntity> queryPermissionsByUser(UserDTO userDto);

    /**
     * @description 查询权限
     * @author 王栋
     * @date 2019/9/26 17:37
     * @param roleDTO
     * @return java.util.List<com.liuyun.liuyunshiro.modules.pojo.enyity.PermissionEntity>
     **/
    List<PermissionEntity> queryRolesByrole(RoleDTO roleDTO);
}
