package com.liuyun.liuyunshiro.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuyun.liuyunshiro.modules.pojo.dto.RoleDTO;
import com.liuyun.liuyunshiro.modules.pojo.dto.UserDTO;
import com.liuyun.liuyunshiro.modules.pojo.enyity.RoleEntity;

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
     * @description 查询角色
     * @author 王栋
     * @date 2019/9/26 16:50
     * @param userDTO
     * @return java.util.List<com.liuyun.liuyunshiro.modules.pojo.dto.RoleDTO>
     **/
    List<RoleDTO> queryAll(UserDTO userDTO);
}
