package com.liuyun.liuyunshiro.modules.pojo.dto;

import lombok.Data;

import java.util.Set;

/**
 * <p>
 * 用户拥有所有角色以及权限
 * </p>
 *
 * @author WangDong
 * @since 2019-09-23
 */
@Data
public class RolePermissionDTO {

    /**
     * 角色
     */
    private Set<String> role;

    /**
     * 权限
     */
    private Set<String> permission;
}
