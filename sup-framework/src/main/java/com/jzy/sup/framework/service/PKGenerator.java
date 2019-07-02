package com.jzy.sup.framework.service;


import com.jzy.sup.framework.annotation.SupTable;

/**
 * <b>功能：</b>主键生成器<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface PKGenerator {

    /**
     * <b>功能描述：</b>生成主键<br>
     * <b>修订记录：</b><br>
     * <li>20190530&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     * @param dbName 数据库名称
     * @param tableName  表名
     * @param keyName 列名
     * @param dealerId   渠道商ID
     * @return 主键
     */
    Long newKey(String dbName, String tableName, String keyName, Integer dealerId);

    /**
     * <b>功能描述：</b>生成主键<br>
     * <b>修订记录：</b><br>
     * <li>20190530&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     * @param dealerId   渠道商ID
     * @return 主键
     */
    Long newKey(Class<?> clazz, Integer dealerId);
}
