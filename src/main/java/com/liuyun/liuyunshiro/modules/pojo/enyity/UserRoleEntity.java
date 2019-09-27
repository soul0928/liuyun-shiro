package com.liuyun.liuyunshiro.modules.pojo.enyity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * <p>
 * 用户角色表
 * </p>
 *
 * @author WangDong
 * @since 2019-09-23
 */
@Data
@Alias("UserRoleEntity")
@TableName("liuyun_user_role")
public class UserRoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;
}
