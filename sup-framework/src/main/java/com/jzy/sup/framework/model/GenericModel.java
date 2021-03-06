package com.jzy.sup.framework.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <b>功能：</b>实体类基类<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190425&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class GenericModel implements Serializable {
    /**
     * 主键
     */
    protected Long id;
    /**
     * 是否删除
     */
    protected Integer delFlag = 0;
    /**
     * 创建时间
     */
    protected Date createTime;
    /**
     * 创建人
     */
    protected Long creatorId;
    /**
     * 修改时间
     */
    protected Date modifyTime;
    /**
     * 修改人
     */
    protected Long modifierId;
}
