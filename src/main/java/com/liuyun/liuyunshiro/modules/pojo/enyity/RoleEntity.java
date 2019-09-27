package com.liuyun.liuyunshiro.modules.pojo.enyity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.liuyun.liuyunshiro.common.base.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author WangDong
 * @since 2019-09-23
 */
@Data
@Alias("RoleEntity")
@TableName("liuyun_role")
@EqualsAndHashCode(callSuper = true)
public class RoleEntity extends DataEntity<RoleEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 权限代码字符串
     */
    private String perCode;

}
