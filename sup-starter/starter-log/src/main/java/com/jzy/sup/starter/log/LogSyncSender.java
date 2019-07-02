package com.jzy.sup.starter.log;


import com.jzy.sup.starter.mq.producer.CommonBusMessageProducer;
import com.jzy.sup.starter.mq.producer.constant.RabbitMqConstant;
import com.jzy.sup.starter.log.bean.LogInfo;
import org.springframework.stereotype.Component;

/**
 * <b>功能：</b><br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Component
public class LogSyncSender extends CommonBusMessageProducer<LogInfo> {

    @Override
    public String queue() {
        return RabbitMqConstant.Queue.QUEUE_LOG;
    }
}
