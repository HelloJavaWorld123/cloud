package com.jzy.sup.redisson.util;


import com.jzy.sup.redisson.RedisConfig;
import com.jzy.sup.redisson.expire.RedisExpiredEventListener;
import com.jzy.sup.redisson.queue.RedisQueueProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.*;
import org.redisson.api.listener.MessageListener;
import org.redisson.client.RedisException;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <b>功能：</b>{@link RedissonClient}工具类，主要对老版本数据兼容性提供支持<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;
 * 变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20180124&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
public class RedissonUtils {


    /**
     * <b>功能描述：</b>删除Bucket对象<br>
     * <b>修订记录：</b><br>
     * <li>20180124&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param redissonClient 客户端实例
     * @param key            关键词
     * @return true为删除成功
     */
    public static boolean delBucket(RedissonClient redissonClient, String key) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        return bucket.isExists() && bucket.delete();
    }

    /**
     * <b>功能描述：</b>删除Bucket对象<br>
     * <b>修订记录：</b><br>
     * <li>20180124&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param bucket {@link RBucket}实例
     * @return true为删除成功
     */
    public static <B extends RBucket> boolean delBucket(B bucket) {
        return bucket != null && bucket.isExists() && bucket.delete();
    }

    /**
     * <b>功能描述：</b>删除RMap对象<br>
     * <b>修订记录：</b><br>
     * <li>20180409&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param map {@link RMap}实例
     * @return true为删除成功
     */
    public static <M extends RMap> boolean delMap(M map) {
        return map != null && map.isExists() && map.delete();
    }

    /**
     * <b>功能描述：</b>根据表达式批量删除<br>
     * <b>修订记录：</b><br>
     * <li>20180124&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param redissonClient 客户端实例
     * @param pattern        表达式
     * @return 删除数量
     */
    public static long delByPattern(RedissonClient redissonClient, String pattern) {
        return redissonClient.getKeys().deleteByPattern(pattern);
    }

    /**
     * <b>功能描述：</b>根据KEY批量删除<br>
     * <b>修订记录：</b><br>
     * <li>20180124&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param redissonClient 客户端实例
     * @param keys           关键词
     * @return 删除数量
     */
    public static long delByKeys(RedissonClient redissonClient, String... keys) {
        RKeys rKeys = redissonClient.getKeys();
        return rKeys.delete(keys);
    }

    /**
     * <b>功能描述：</b>根据表达式批量设置有效期<br>
     * <b>修订记录：</b><br>
     * <li>20180124&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param redissonClient 客户端实例
     * @param pattern        表达式
     * @param expireTime     过期时间(单位秒）
     */
    public static void expireByPattern(RedissonClient redissonClient, String pattern, long expireTime) {
        redissonClient.getKeys()
                .findKeysByPattern(pattern)
                .forEach(key -> redissonClient.getKeys().expire(key, expireTime, TimeUnit.SECONDS));
    }

    /**
     * <b>功能描述：</b>安全获取数据，用于兼容老版本，忽略异常<br>
     * <b>修订记录：</b><br>
     * <li>20180125&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param bucket 数据源
     * @return 数据
     */
    public static <V> V getIfPossible(RBucket<V> bucket) {
        try {
            return bucket != null && bucket.isExists() ? bucket.get() : null;
        } catch (RedisException ignored) {
            // 兼容老版本数据，如果转换不过来忽略，重新进行缓存
        }

        return null;
    }

    /**
     * <b>功能描述：</b>安全获取数据，用于兼容老版本，忽略异常<br>
     * <b>修订记录：</b><br>
     * <li>20180125&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param list 数据源
     * @return 数据
     */
    public static <V> List<V> getIfPossible(RList<V> list) {
        try {
            return list != null && list.isExists() && isList(list) ? list : null;
        } catch (RedisException ignored) {
            // 兼容老版本数据，如果转换不过来忽略，重新进行缓存
        }

        return null;
    }

    /**
     * <b>功能描述：</b>安全获取数据，用于兼容老版本，忽略异常<br>
     * <b>修订记录：</b><br>
     * <li>20180125&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param map 数据源
     * @return 数据
     */
    public static <K, V> V getIfPossible(RMap<K, V> map, K key) {
        try {
            return map != null && map.isExists() && map.containsKey(key) ? map.get(key) : null;
        } catch (RedisException ignored) {
            // 兼容老版本数据，如果转换不过来忽略，重新进行缓存
        }

        return null;
    }

    /**
     * <b>功能描述：</b>安全获取数据，用于兼容老版本，忽略异常<br>
     * <b>修订记录：</b><br>
     * <li>20180125&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param redissonClient 客户端实例
     * @param key            关键词
     * @return 数据
     */
    public static <V> V getBucket(RedissonClient redissonClient, String key) {
        return getIfPossible(redissonClient.getBucket(key));
    }

    /**
     * <b>功能描述：</b>设置{@link RBucket}对象<br>
     * <b>修订记录：</b><br>
     * <li>20180208&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param redissonClient 客户端实例
     * @param key            关键词
     * @param value          对象
     */
    public static <V> void setBucket(RedissonClient redissonClient, String key, V value) {
        if (StringUtils.isNotBlank(key) && value != null) {
            RBucket<V> bucket = redissonClient.getBucket(key);
            bucket.set(value);
        }
    }

    /**
     * <b>功能描述：</b>设置{@link RBucket}对象<br>
     * <b>修订记录：</b><br>
     * <li>20180208&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param redissonClient 客户端实例
     * @param key            关键词
     * @param value          对象
     * @param second         过期时间，单位秒
     */
    public static <V> void setBucket(RedissonClient redissonClient, String key, V value, long second) {
        if (StringUtils.isNotBlank(key) && value != null) {
            RBucket<V> bucket = redissonClient.getBucket(key);
            bucket.set(value, second, TimeUnit.SECONDS);
        }
    }

    /**
     * <b>功能描述：</b>设置{@link RBucket}对象<br>
     * <b>修订记录：</b><br>
     * <li>20180208&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param redissonClient 客户端实例
     * @param key            关键词
     * @param value          对象
     */
    public static <V> boolean trySetBucket(RedissonClient redissonClient, String key, V value) {
        if (StringUtils.isNotBlank(key) && value != null) {
            RBucket<V> bucket = redissonClient.getBucket(key);
            return bucket.trySet(value);
        }

        return false;
    }

    /**
     * <b>功能描述：</b>安全获取数据，用于兼容老版本，忽略异常<br>
     * <b>修订记录：</b><br>
     * <li>20180125&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param redissonClient 客户端实例
     * @param key            关键词
     * @return 数据
     */
    public static <V> List<V> getList(RedissonClient redissonClient, String key) {
        return getIfPossible(redissonClient.getList(key));
    }

    /**
     * <b>功能描述：</b>设置{@link RList}对象<br>
     * <b>修订记录：</b><br>
     * <li>20180208&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param redissonClient 客户端实例
     * @param key            关键词
     * @param value          对象
     */
    public static <V> void setList(RedissonClient redissonClient, String key, List<V> value) {
        if (StringUtils.isNotBlank(key) && !CollectionUtils.isEmpty(value)) {
            RList<V> list = redissonClient.getList(key);
            list.clear();
            list.addAll(value);
        }
    }

    /**
     * <b>功能描述：</b>设置{@link RList}对象<br>
     * <b>修订记录：</b><br>
     * <li>20180208&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param redissonClient 客户端实例
     * @param key            关键词
     * @param value          对象
     * @param second         过期时间，单位秒
     */
    public static <V> void setList(RedissonClient redissonClient, String key, List<V> value, long second) {
        if (StringUtils.isNotBlank(key) && !CollectionUtils.isEmpty(value)) {
            RList<V> list = redissonClient.getList(key);
            list.clear();
            list.addAll(value);
            list.expire(second, TimeUnit.SECONDS);
        }
    }

    /**
     * <b>功能描述：</b>给{@link RList}对象添加值<br>
     * <b>修订记录：</b><br>
     * <li>20180208&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param redissonClient 客户端实例
     * @param key            关键词
     * @param value          对象
     */
    public static <V> void addToList(RedissonClient redissonClient, String key, V value) {
        if (StringUtils.isNotBlank(key) && value != null) {
            RList<V> list = redissonClient.getList(key);
            list.add(value);
        }
    }

    /**
     * <b>功能描述：</b>给{@link RList}对象添加值<br>
     * <b>修订记录：</b><br>
     * <li>20180208&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param redissonClient 客户端实例
     * @param key            关键词
     * @param value          对象
     * @param second         过期时间，单位秒
     */
    public static <V> void addToList(RedissonClient redissonClient, String key, V value, long second) {
        if (StringUtils.isNotBlank(key) && value != null) {
            RList<V> list = redissonClient.getList(key);
            list.add(value);
            list.expire(second, TimeUnit.SECONDS);
        }
    }

    /**
     * <b>功能描述：</b>给{@link RMap}对象添加键值对<br>
     * <b>修订记录：</b><br>
     * <li>20180208&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param redissonClient 客户端实例
     * @param key            关键词
     * @param value          对象
     */
    public static <K, V> void putToMap(RedissonClient redissonClient, String key, K mapKey, V value) {
        if (StringUtils.isNotBlank(key) && value != null) {
            RMap<K, V> rMap = redissonClient.getMap(key);
            rMap.put(mapKey, value);
        }
    }

    /**
     * <b>功能描述：</b>给{@link RMap}对象添加键值对<br>
     * <b>修订记录：</b><br>
     * <li>20180208&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param redissonClient 客户端实例
     * @param key            关键词
     * @param value          对象
     * @param second         过期时间，单位秒
     */
    public static <K, V> void putToMap(RedissonClient redissonClient, String key, K mapKey, V value, long second) {
        if (StringUtils.isNotBlank(key) && value != null) {
            RMap<K, V> rMap = redissonClient.getMap(key);
            rMap.put(mapKey, value);
            rMap.expire(second, TimeUnit.SECONDS);
        }
    }

    /**
     * <b>功能描述：</b>安全获取数据，用于兼容老版本，忽略异常<br>
     * <b>修订记录：</b><br>
     * <li>20180125&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param redissonClient 客户端实例
     * @param key            关键词
     * @return 数据
     */
    public static <V> V getMapValue(RedissonClient redissonClient, String key, V mapKey) {
        return getIfPossible(redissonClient.getMap(key), mapKey);
    }


    /**
     * <b>功能描述：</b>安全获取数据，用于兼容老版本，忽略异常<br>
     * <b>修订记录：</b><br>
     * <li>20180202&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public static <K, V> Map<K, V> readAllMapIfPossible(RMap<K, V> rMap) {
        try {
            return rMap.readAllMap();
        } catch (RedisException ignored) {
            // 兼容老版本数据，如果转换不过来忽略，重新进行缓存
        }

        return null;
    }

    /**
     * <b>功能描述：</b>是否是一个规范的{@link RList} 对象<br>
     * <b>修订记录：</b><br>
     * <li>20180207&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param rList 客户端实例
     * @return true 为规范的{@link RList} 对象
     */
    public static <V> boolean isList(RList<V> rList) {
        try {
            rList.toArray();
            return true;
        } catch (RedisException ignored) {
            // 兼容老版本数据，如果转换不过来忽略，重新进行缓存
        }

        return false;
    }

    /**
     * <b>功能描述：</b>清理不规范的{@link RList} 对象<br>
     * <b>修订记录：</b><br>
     * <li>20180208&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param rList 客户端实例
     * @return true 清理成功
     */
    public static <V> boolean clearIfPossible(RList<V> rList) {
        if (rList != null && !isList(rList)) {
            rList.clear();

            return true;
        }

        return false;
    }

    /**
     * <b>功能描述：</b>当前key是否存在redis中<br>
     * <b>修订记录：</b><br>
     * <li>20180213&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public static <V> boolean isExist(RedissonClient redissonClient, String key) {
        RBucket<V> bucket = redissonClient.getBucket(key);
        return bucket.isExists();
    }

    /**
     * <b>功能描述：</b>订阅主题<br>
     * <b>修订记录：</b><br>
     * <li>20180530&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param topic           订阅的具体主题
     * @param messageListener 消息实现类
     */
    public static <T> void subscription(String topic, RedissonClient redissonClient, MessageListener<T> messageListener) {
        redissonClient.getTopic(topic).addListener((MessageListener<Object>) messageListener);
    }

    /**
     * <b>功能描述：</b>取消主题<br>
     * <b>修订记录：</b><br>
     * <li>20180530&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param topic           订阅的具体主题
     * @param messageListener 消息实现类
     */
    public static void unSubscription(String topic, RedissonClient redissonClient, MessageListener<?> messageListener) {
        redissonClient.getTopic(topic).removeListener(messageListener);
    }

    /**
     * <b>功能描述：</b>发布主题<br>
     * <b>修订记录：</b><br>
     * <li>20180530&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param topic        订阅的具体主题
     * @param redisMsgBody 主题中的内容
     */
    public static <T> void publish(String topic, T redisMsgBody, RedissonClient redissonClient) {
        redissonClient.getTopic(topic).publish(redisMsgBody);
    }


    /**
     * <b>功能描述：</b>对某个map进行监听，监听某个key。此方法调用多次会监听多次，如果写了@RedisKeyExpire注解，就不需要手动调用此方法<br>
     * <b>修订记录：</b><br>
     * <li>20180530&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     * <li>20180531&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;修改map中key</li><br>
     */
    public static void keyExpireListener(String mapName, RedissonClient redissonClient, Boolean singleProcessor) {
        final String existKey = "_existKey";
        RMapCache map = redissonClient.getMapCache(mapName);
        if (map.isEmpty()) {
            if (singleProcessor && redissonClient.getBucket(mapName + existKey).trySet(1, 300, TimeUnit.SECONDS)) {
                map.addListener(new RedisExpiredEventListener());
            } else {
                log.info("{}已被监听，不能重复监听", mapName);
            }
            if (!singleProcessor) {
                map.addListener(new RedisExpiredEventListener());
            }
        }
    }


    /**
     * <b>功能描述：</b>给延迟队列增加元素<br>
     * <b>修订记录：</b><br>
     * <li>20180625&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param queueName      队列名称
     * @param value          值
     * @param second         延迟的秒数
     * @param redissonClient redisson客户端
     */
    public static <T> void delayQueue(String queueName, T value, Integer second, RedissonClient redissonClient) {
        RQueue<T> queue = redissonClient.getBlockingQueue(queueName);
        RDelayedQueue dq = redissonClient.getDelayedQueue(queue);
        if (dq.contains(value)) {
            log.info("[ REDIS ]队列{}中已经有该对象：{}", queueName, value);
            dq.remove(value);
        }
        dq.offer(value, second, TimeUnit.SECONDS);
    }

    /**
     * <b>功能描述：</b>监听某个队列<br>
     * <b>修订记录：</b><br>
     * <li>20180625&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     * @param queueName 队列名称
     * @param second 等待多少秒
     * @param redissonClient redisson客户端
     */
    public static void listenQueue(String queueName, Integer second, RedissonClient redissonClient) {
        new ThreadPoolExecutor(1, 50, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(20), new ThreadPoolExecutor.AbortPolicy()).execute(new Runnable() {
            @Override
            public void run() {
                log.info("[ REDIS ]监听队列：{}", queueName);
                getQueueData(queueName, second, redissonClient);
            }
        });
    }

    private static void getQueueData(String queueName, Integer second, RedissonClient redissonClient) {
        while (true) {
            RQueue queue = redissonClient.getBlockingQueue(queueName);
            redissonClient.getDelayedQueue(queue);
            queue.pollAsync().thenAccept(t -> {
                if (t != null) {
                    Set<RedisQueueProcessor> processors = RedisConfig.getQueueProcessor(queueName);
                    for (RedisQueueProcessor r : processors) {
                        log.info("[ REDIS ]队列{}进行处理：{}", queueName, t);
                        r.process(t);
                    }
                }
            });
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }
    }


    /**
     * <b>功能描述：</b>对某个监听map增加key和value<br>
     * <b>修订记录：</b><br>
     * <li>20180530&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param mapName        监听的map名称
     * @param key            键
     * @param value          值
     * @param second         几秒钟过期
     * @param redissonClient
     */
    public static <T> void keyExpire(String mapName, String key, T value, Integer second, RedissonClient redissonClient) {
        RMapCache map = redissonClient.getMapCache(mapName);
        map.put(key, value, second, TimeUnit.SECONDS);
    }

}
