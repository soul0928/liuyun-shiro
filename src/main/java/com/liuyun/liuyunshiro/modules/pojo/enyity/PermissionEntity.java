package com.liuyun.liuyunshiro.modules.pojo.enyity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liuyun.liuyunshiro.common.base.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;


/**
 * <p>
 * 资源表
 * </p>
 *
 * @author WangDong
 * @since 2019-09-23
 */
@Data
@Alias("PermissionEntity")
@TableName("liuyun_permission")
@EqualsAndHashCode(callSuper = true)
public class PermissionEntity extends DataEntity<PermissionEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 权限代码字符串
     */
    private String perCode;

}
