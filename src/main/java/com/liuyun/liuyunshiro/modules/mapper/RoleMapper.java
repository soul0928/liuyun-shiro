package com.liuyun.liuyunshiro.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuyun.liuyunshiro.modules.pojo.dto.UserDTO;
import com.liuyun.liuyunshiro.modules.pojo.enyity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
     * @description 查询用户拥有角色
     * @author 王栋
     * @date 2019/9/26 16:08
     * @param userDto
     * @return java.util.List<RoleEntity>
     **/
    List<RoleEntity> queryRolesByUser(UserDTO userDto);
}
