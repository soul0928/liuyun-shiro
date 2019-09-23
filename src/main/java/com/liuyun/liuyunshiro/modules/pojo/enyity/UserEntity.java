package com.liuyun.liuyunshiro.modules.pojo.enyity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.liuyun.liuyunshiro.common.base.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author WangDong
 * @since 2019-09-23
 */
@Data
@Alias("UserEntity")
@TableName("liuyun_user")
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends DataEntity<UserEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 姓名
     */
    private String namr;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

}
