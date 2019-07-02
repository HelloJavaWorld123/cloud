package com.jzy.sup.starter.mq.producer;


import com.jzy.sup.common.util.JSONUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * <b>功能：</b>AbstractRabbitMessageProducer<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public abstract class AbstractRabbitMessageProducer<T extends Serializable> implements MessageProducer <T>{

    @Autowired
    protected AmqpTemplate rabbitTemplate;

    @Override
    public void produce(T t) {
        this.rabbitTemplate.convertAndSend(exchange(),queue(), JSONUtil.toStr(t));
    }

    public abstract String exchange();

    public abstract String queue();


}
