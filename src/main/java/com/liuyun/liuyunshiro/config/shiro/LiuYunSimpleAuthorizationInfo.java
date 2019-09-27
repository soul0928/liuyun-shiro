package com.liuyun.liuyunshiro.config.shiro;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.shiro.authz.SimpleAuthorizationInfo;

import java.io.Serializable;

/**
 * @ProjectName liuyun-shiro
 * @ClassName LiuYunSimpleAuthorizationInfo
 * @Description 实现序列化存志 reids
 * @Author WangDong
 * @Date 2019/9/25 16:51
 * @Version 2.1.3
 **/
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class LiuYunSimpleAuthorizationInfo extends SimpleAuthorizationInfo implements Serializable {

    private static final long serialVersionUID = 1L;


}


