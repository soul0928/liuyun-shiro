package com.liuyun.liuyunshiro.modules.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuyun.liuyunshiro.common.result.Result;
import com.liuyun.liuyunshiro.common.util.id.IdUtil;
import com.liuyun.liuyunshiro.modules.pojo.dto.RoleDTO;
import com.liuyun.liuyunshiro.modules.pojo.dto.UserDTO;
import com.liuyun.liuyunshiro.modules.pojo.enyity.RoleEntity;
import com.liuyun.liuyunshiro.modules.pojo.enyity.RolePermissionEntity;
import com.liuyun.liuyunshiro.modules.pojo.enyity.UserRoleEntity;
import com.liuyun.liuyunshiro.modules.service.RolePermissionService;
import com.liuyun.liuyunshiro.modules.service.RoleService;
import com.liuyun.liuyunshiro.modules.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author WangDong
 * @since 2019-09-23
 */
@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * @description 新增角色
     * @author 王栋
     * @date 2019/9/26 16:37
     * @param roleEntity
     * @return com.liuyun.liuyunshiro.common.result.Result
     **/
    @PostMapping("add")
    public Result add(RoleEntity roleEntity) {
        roleEntity.setId(IdUtil.getNextId());
        roleEntity.beforeInsert();
        roleService.save(roleEntity);
        return Result.success();
    }

    /**
     * @description 修改角色
     * @author 王栋
     * @date 2019/9/26 16:37
     * @param roleEntity
     * @return com.liuyun.liuyunshiro.common.result.Result
     **/
    @PutMapping("update")
    public Result update(RoleEntity roleEntity) {
        QueryWrapper<UserRoleEntity> userRolewrapper = new QueryWrapper<>();
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setRoleId(roleEntity.getId());
        userRolewrapper.setEntity(userRoleEntity);
        List<UserRoleEntity> userRoleList = userRoleService.list(userRolewrapper);
        if (userRoleList.size() > 0) {
            return Result.fail("当前上有绑定此角色的用户， 请取消关联后，在进行修改操作");
        }
        QueryWrapper<RolePermissionEntity> rolePermissionWrapper = new QueryWrapper<>();
        RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
        rolePermissionEntity.setRoleId(roleEntity.getId());
        rolePermissionWrapper.setEntity(rolePermissionEntity);
        List<RolePermissionEntity> rolePermissionList = rolePermissionService.list(rolePermissionWrapper);
        if (userRoleList.size() > 0) {
            return Result.fail("当前上有绑定此角色的权限， 请取消关联后，在进行修改操作");
        }
        roleEntity.beforeUpdate();
        roleService.updateById(roleEntity);
        return Result.success();
    }

    /**
     * @description 删除角色
     * @author 王栋
     * @date 2019/9/26 16:37
     * @param roleEntity
     * @return com.liuyun.liuyunshiro.common.result.Result
     **/
    @DeleteMapping("del")
    public Result del(RoleEntity roleEntity) {
        QueryWrapper<UserRoleEntity> userRolewrapper = new QueryWrapper<>();
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setRoleId(roleEntity.getId());
        userRolewrapper.setEntity(userRoleEntity);
        List<UserRoleEntity> userRoleList = userRoleService.list(userRolewrapper);
        if (userRoleList.size() > 0) {
            return Result.fail("当前上有绑定此角色的用户， 请取消关联后，在进行删除操作");
        }
        QueryWrapper<RolePermissionEntity> rolePermissionWrapper = new QueryWrapper<>();
        RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
        rolePermissionEntity.setRoleId(roleEntity.getId());
        rolePermissionWrapper.setEntity(rolePermissionEntity);
        List<RolePermissionEntity> rolePermissionList = rolePermissionService.list(rolePermissionWrapper);
        if (userRoleList.size() > 0) {
            return Result.fail("当前上有绑定此角色的权限， 请取消关联后，在进行删除操作");
        }
        roleEntity.beforeDelete();
        roleService.updateById(roleEntity);
        return Result.success();
    }

    /**
     * @description 查询角色
     * @author 王栋
     * @date 2019/9/26 16:37
     * @return com.liuyun.liuyunshiro.common.result.Result
     **/
    @GetMapping("queryAll")
    public Result queryAll(UserDTO userDTO) {
        List<RoleDTO> roleDTOs = roleService.queryAll(userDTO);
        return Result.success(roleDTOs);
    }

}
