package com.liuyun.liuyunshiro.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuyun.liuyunshiro.modules.pojo.enyity.UserRoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author WangDong
 * @since 2019-09-23
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleEntity> {

}
