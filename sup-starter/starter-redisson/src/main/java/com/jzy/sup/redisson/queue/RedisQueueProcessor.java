package com.jzy.sup.redisson.queue;

/**
 * <b>功能：</b>redis队列处理类接口<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2018/6/22 0022$&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface RedisQueueProcessor<T> {

    /**
     * <b>功能描述：</b>处理方法<br>
     * <b>修订记录：</b><br>
     * <li>20180625&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param value 内容
     * @return
     */
    void process(T value);

}
