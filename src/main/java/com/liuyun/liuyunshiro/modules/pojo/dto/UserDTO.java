package com.liuyun.liuyunshiro.modules.pojo.dto;

import lombok.Data;

/**
 * @ProjectName liuyun-shiro
 * @ClassName UserDTO
 * @Description 登录请求参数
 * @Author WangDong
 * @Date 2019/9/23 13:57
 * @Version 2.1.3
 **/
@Data
public class UserDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

}
