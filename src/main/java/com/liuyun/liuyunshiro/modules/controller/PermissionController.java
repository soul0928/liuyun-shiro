package com.liuyun.liuyunshiro.modules.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuyun.liuyunshiro.common.result.Result;
import com.liuyun.liuyunshiro.common.util.id.IdUtil;
import com.liuyun.liuyunshiro.modules.pojo.dto.PermissionDTO;
import com.liuyun.liuyunshiro.modules.pojo.dto.RoleDTO;
import com.liuyun.liuyunshiro.modules.pojo.enyity.PermissionEntity;
import com.liuyun.liuyunshiro.modules.pojo.enyity.RolePermissionEntity;
import com.liuyun.liuyunshiro.modules.service.PermissionService;
import com.liuyun.liuyunshiro.modules.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author WangDong
 * @since 2019-09-23
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionService rolePermissionService;


    /**
     * @description 新增权限
     * @author 王栋
     * @date 2019/9/26 16:37
     * @param permissionEntity
     * @return com.liuyun.liuyunshiro.common.result.Result
     **/
    @PostMapping("add")
    public Result add(PermissionEntity permissionEntity) {
        permissionEntity.setId(IdUtil.getNextId());
        permissionEntity.beforeInsert();
        permissionService.save(permissionEntity);
        return Result.success();
    }

    /**
     * @description 修改权限
     * @author 王栋
     * @date 2019/9/26 16:37
     * @param permissionEntity
     * @return com.liuyun.liuyunshiro.common.result.Result
     **/
    @PutMapping("update")
    public Result update(PermissionEntity permissionEntity) {
        QueryWrapper<RolePermissionEntity> rolePermissionWrapper = new QueryWrapper<>();
        RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
        rolePermissionEntity.setPermissionId(permissionEntity.getId());
        rolePermissionWrapper.setEntity(rolePermissionEntity);
        List<RolePermissionEntity> rolePermissionList = rolePermissionService.list(rolePermissionWrapper);
        if (rolePermissionList.size() > 0) {
            return Result.fail("当前上有绑定此权限的角色， 请取消关联后，在进行修改操作");
        }
        permissionEntity.beforeUpdate();
        permissionService.updateById(permissionEntity);
        return Result.success();
    }

    /**
     * @description 删除权限
     * @author 王栋
     * @date 2019/9/26 16:37
     * @param permissionEntity
     * @return com.liuyun.liuyunshiro.common.result.Result
     **/
    @DeleteMapping("del")
    public Result del(PermissionEntity permissionEntity) {
        QueryWrapper<RolePermissionEntity> rolePermissionWrapper = new QueryWrapper<>();
        RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
        rolePermissionEntity.setPermissionId(permissionEntity.getId());
        rolePermissionWrapper.setEntity(rolePermissionEntity);
        List<RolePermissionEntity> rolePermissionList = rolePermissionService.list(rolePermissionWrapper);
        if (rolePermissionList.size() > 0) {
            return Result.fail("当前上有绑定此权限的角色， 请取消关联后，在进行删除操作");
        }
        permissionEntity.beforeDelete();
        permissionService.updateById(permissionEntity);
        return Result.success();
    }

    /**
     * @description 查询权限
     * @author 王栋
     * @date 2019/9/26 16:37
     * @return com.liuyun.liuyunshiro.common.result.Result
     **/
    @GetMapping("queryAll")
    public Result queryAll(RoleDTO roleDTO) {
        List<PermissionDTO> permissionDTOs = permissionService.queryAll(roleDTO);
        return Result.success(permissionDTOs);
    }

}
