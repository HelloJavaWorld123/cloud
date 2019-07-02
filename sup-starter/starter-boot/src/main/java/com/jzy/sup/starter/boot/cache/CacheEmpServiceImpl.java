package com.jzy.sup.starter.boot.cache;

import com.jzy.sup.framework.cache.model.EmpCache;
import com.jzy.sup.framework.cache.service.CacheEmpService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <b>功能：</b>员工信息缓存<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190528&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
@Service
public class CacheEmpServiceImpl implements CacheEmpService {

    @Resource
    private RedissonClient redissonClient;

    /**
     * <b>功能描述：</b>根据key获取缓存的员工信息<br>
     * <b>修订记录：</b><br>
     * <li>20190528&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public EmpCache getCacheEmpByKey(String key) {
        RBucket<EmpCache> empCacheRBucket = redissonClient.getBucket(key);
        return empCacheRBucket.get();
    }
}
