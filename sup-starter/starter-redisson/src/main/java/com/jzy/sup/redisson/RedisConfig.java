package com.jzy.sup.redisson;


import com.jzy.sup.redisson.expire.RedisKeyExpire;
import com.jzy.sup.redisson.expire.RedisKeyExpireProcessor;
import com.jzy.sup.redisson.queue.RedisQueueProcess;
import com.jzy.sup.redisson.queue.RedisQueueProcessor;
import com.jzy.sup.redisson.subscribe.RedisSubscribe;
import com.jzy.sup.redisson.util.RedissonUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <b>功能：</b>redis配置类<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20180530&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
@Component
public class RedisConfig implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * map名称和处理类对应
     */
    private static Map<String, Set<RedisKeyExpireProcessor>> processorMap = new ConcurrentHashMap<>();

    private static Map<String, Set<RedisQueueProcessor>> queueProcessorMap = new ConcurrentHashMap<>();

    @Autowired
    private RedissonClient redissonClient;

    /**
     * <b>功能描述：</b>Spring初始化完成之后执行<br>
     * <b>修订记录：</b><br>
     * <li>20180530&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        scanAnnotation(event);
    }

    /**
     * <b>功能描述：</b>扫描RedisKeyExpire注解，加入processorMap中<br>
     * <b>修订记录：</b><br>
     * <li>20180531&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param event 应用启动完成事件对象
     */
    private void scanAnnotation(ContextRefreshedEvent event) {
        scanKeyExpireAnnotation(event);
        scanSubscribeAnnotation(event);
        scanQueueAnnotation(event);
    }

    /**
     * <b>功能描述：</b>扫描键过期处理类<br>
     * <b>修订记录：</b><br>
     * <li>20180531&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param event 应用启动完成事件对象
     */
    private void scanKeyExpireAnnotation(ContextRefreshedEvent event) {
        // 通过注解获取相关的类
        Map<String, Object> map = new HashMap();
        map = event.getApplicationContext().getBeansWithAnnotation(RedisKeyExpire.class);
        for (Map.Entry<String, Object> entrymap : map.entrySet()) {
            try {
                RedisKeyExpire redisKeyExpireProcessor = entrymap.getValue().getClass().getDeclaredAnnotation(RedisKeyExpire.class);
                String[] topics = redisKeyExpireProcessor.topics();
                for (String topic : topics) {
                    Set<RedisKeyExpireProcessor> redisKeyExpireProcessors = processorMap.getOrDefault(topic, new HashSet<>());
                    redisKeyExpireProcessors.add((RedisKeyExpireProcessor) entrymap.getValue());
                    processorMap.put(topic, redisKeyExpireProcessors);
                    RedissonUtils.keyExpireListener(topic, redissonClient, true);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    private void scanQueueAnnotation(ContextRefreshedEvent event) {
        // 通过注解获取相关的类
        Map<String, Object> map = new HashMap();
        map = event.getApplicationContext().getBeansWithAnnotation(RedisQueueProcess.class);
        for (Map.Entry<String, Object> entrymap : map.entrySet()) {
            try {
                RedisQueueProcess redisQueueProcessor = entrymap.getValue().getClass().getDeclaredAnnotation(RedisQueueProcess.class);
                String[] topics = redisQueueProcessor.topics();
                for (String topic : topics) {
                    Set<RedisQueueProcessor> redisQueueProcessors = queueProcessorMap.getOrDefault(topic, new HashSet<>());
                    redisQueueProcessors.add((RedisQueueProcessor) entrymap.getValue());
                    queueProcessorMap.put(topic, redisQueueProcessors);

                    RedissonUtils.listenQueue(topic, 10, redissonClient);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    /**
     * <b>功能描述：</b>扫描订阅方法<br>
     * <b>修订记录：</b><br>
     * <li>20180531&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param event 应用启动完成事件对象
     */
    private void scanSubscribeAnnotation(ContextRefreshedEvent event) {
        Map<String, Object> map = event.getApplicationContext().getBeansWithAnnotation(RedisSubscribe.class);
        for (Map.Entry<String, Object> entryMap : map.entrySet()) {
            RedisSubscribe redisSubscribeProcessor = entryMap.getValue().getClass().getDeclaredAnnotation(RedisSubscribe.class);
            String[] topics = redisSubscribeProcessor.topics();
            for (String topic : topics) {
                RedissonUtils.subscription(topic, redissonClient, (MessageListener) entryMap.getValue());
            }
        }
    }

    /**
     * <b>功能描述：</b>根据map名称获取处理类<br>
     * <b>修订记录：</b><br>
     * <li>20180530&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param topic 主题名称
     */
    public static Set<RedisKeyExpireProcessor> getProcessor(String topic) {
        return processorMap.get(topic);
    }

    public static Set<RedisQueueProcessor> getQueueProcessor(String topic) {
        return queueProcessorMap.get(topic);
    }

}
