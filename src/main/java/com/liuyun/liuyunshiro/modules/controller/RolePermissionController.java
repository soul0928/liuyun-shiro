package com.liuyun.liuyunshiro.modules.controller;


import com.liuyun.liuyunshiro.modules.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色资源表 前端控制器
 * </p>
 *
 * @author WangDong
 * @since 2019-09-23
 */
@RestController
@RequestMapping("/rolePermission")
public class RolePermissionController {

    @Autowired
    private RolePermissionService rolePermissionService;

}
