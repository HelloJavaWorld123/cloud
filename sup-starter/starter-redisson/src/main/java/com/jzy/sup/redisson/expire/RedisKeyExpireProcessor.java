package com.jzy.sup.redisson.expire;

/**
 * <b>功能：</b>redis键过期处理类接口<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20180530&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface RedisKeyExpireProcessor {

    /**
     * <b>功能描述：</b>redis键过期之后的处理<br>
     * <b>修订记录：</b><br>
     * <li>20180531&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param key RMapCache对象中的key
     * @param value RMapCache对象中的key对应的value值
     */
    <T> void process(String key, T value);

}
