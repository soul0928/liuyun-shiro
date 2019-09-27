package com.liuyun.liuyunshiro.modules.pojo.dto;

import lombok.Data;


/**
 * <p>
 * 资源表
 * </p>
 *
 * @author WangDong
 * @since 2019-09-23
 */
@Data
public class PermissionDTO {

    /**
     * ID
     */
    private Long id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 权限代码字符串
     */
    private String perCode;

}
