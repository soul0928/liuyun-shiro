package com.liuyun.liuyunshiro.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuyun.liuyunshiro.modules.pojo.enyity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author WangDong
 * @since 2019-09-23
 */
@Mapper
public interface RoleMapper extends BaseMapper<RoleEntity> {

    /**
     * @description 查询用户所有权限
     * @author 王栋
     * @date 2019/9/25 14:17
     * @param userDto
     * @return java.util.List<com.liuyun.liuyunshiro.modules.pojo.enyity.RoleEntity>
     **/
    //List<RoleEntity> queryRoleByUser(UserDTO userDto);
}
