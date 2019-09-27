package com.liuyun.liuyunshiro.modules.pojo.enyity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * <p>
 * 角色资源表
 * </p>
 *
 * @author WangDong
 * @since 2019-09-23
 */
@Data
@Alias("RolePermissionEntity")
@TableName("liuyun_role_permission")
public class RolePermissionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 权限id
     */
    private Long permissionId;
}
