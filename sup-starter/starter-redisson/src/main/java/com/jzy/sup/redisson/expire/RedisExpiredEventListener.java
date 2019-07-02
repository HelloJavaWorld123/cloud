package com.jzy.sup.redisson.expire;


import com.jzy.sup.redisson.RedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.map.event.EntryEvent;
import org.redisson.api.map.event.EntryExpiredListener;
import org.springframework.stereotype.Component;

import java.util.Set;


/**
 * <b>功能：</b>redis键过期监听类<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20180530&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
@Component
public class RedisExpiredEventListener implements EntryExpiredListener<String,Object>{

    /**
     * <b>功能描述：</b>键过期事件对象<br>
     * <b>修订记录：</b><br>
     * <li>20180605&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     * @param event
     * @return void
     */
    @Override
    public void onExpired(EntryEvent<String,Object> event) {
        Set<RedisKeyExpireProcessor> processors = RedisConfig.getProcessor(event.getSource().getName().trim());
        for (RedisKeyExpireProcessor processor:processors) {
            processor.process(event.getKey(),event.getValue());
        }
    }
}
