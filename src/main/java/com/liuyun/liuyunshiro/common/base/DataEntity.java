package com.liuyun.liuyunshiro.common.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ProjectName liuyun-shiro
 * @ClassName DataEntity
 * @Description 公共实体属性
 * @Author WangDong
 * @Date 2019/9/23 10:26
 * @Version 2.1.3
 **/
@Data
public abstract class DataEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 删除标记（0：正常；1：删除；）
     */
    public static final Integer DEL_FLAG_NORMAL = 0;
    public static final Integer DEL_FLAG_DELETE = 1;

    /**
     * 创建者
     */
    protected Long createUserId;
    /**
     * 创建日期
     */
    protected Date createTime;
    /**
     * 更新者
     */
    protected Long updateUserId;
    /**
     * 更新日期
     */
    protected Date updateTime;
    /**
     * 备注
     */
    protected String remark;
    /**
     * 版本
     */
    protected Integer version;
    /**
     * 删除标记（0：正常；1：删除;）
     */
    protected Integer delFlag;

    public void beforeInsert(){
        this.createTime = new Date();
        this.createUserId = 1L;
        this.version = 0;
        this.delFlag = DataEntity.DEL_FLAG_NORMAL;
    }

    public void beforeUpdate(){
        this.updateTime = new Date();
        this.updateUserId = 1L;
    }

    public DataEntity() {
        super();
    }
}


