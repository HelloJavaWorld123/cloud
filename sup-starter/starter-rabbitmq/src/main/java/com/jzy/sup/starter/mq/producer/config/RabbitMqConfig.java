package com.jzy.sup.starter.mq.producer.config;


import com.jzy.sup.starter.mq.producer.constant.RabbitMqConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <b>功能：</b><br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Configuration
public class RabbitMqConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory);
    }


    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactoryAck() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //开启手动 ack
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }


    @Bean
    public RabbitTemplate rabbitTemplate(){
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                logger.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                logger.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);
            }
        });
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    @Bean
    public TopicExchange initExchange() {
        return new TopicExchange(RabbitMqConstant.Exchange.EXCHANGE_COMMON);
    }

    @Bean(name = "mq_queue_scan_pay_settle")
    public Queue queueMessageSettleWuu() {
        return new Queue(RabbitMqConstant.Queue.QUEUE_SETTLE_WUU);
    }

    @Bean(name = "mq_queue_settle")
    public Queue queueMessageSettle() {
        return new Queue(RabbitMqConstant.Queue.QUEUE_SETTLE);
    }

    /**
     * <b>功能描述：</b>声明退单队列<br>
     * <b>修订记录：</b><br>
     * <li>20180806&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Bean(name = "mq_queue_refund")
    public Queue queueMessageRefund() {
        return new Queue(RabbitMqConstant.Queue.QUEUE_REFUND);
    }

    /**
     * <b>功能描述：</b>声明菜品详情跑批<br>
     * <b>修订记录：</b><br>
     * <li>20180814&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Bean(name = "mq_queue_item_detail")
    public Queue queueMessageItemDetail() {
        return new Queue(RabbitMqConstant.Queue.QUEUE_ITEM_DETAIL);
    }

    /**
     * <b>功能描述：</b>测试<br>
     * <b>修订记录：</b><br>
     * <li>20180806&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Bean(name = "mq_queue_test")
    public Queue queueMessageTest() {
        return new Queue(RabbitMqConstant.Queue.QUEUE_SETTLE_TEST, true);
    }

    /**
     * <b>功能描述：</b>测试<br>
     * <b>修订记录：</b><br>
     * <li>20180814&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Bean
    public Binding bindingExchangeMessageTest(@Qualifier("mq_queue_test") Queue queueMessageNotify, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessageNotify).to(exchange).with(RabbitMqConstant.Queue.QUEUE_SETTLE_TEST + ".#");
    }

    /**
     * <b>功能描述：</b>声明菜品详情跑批<br>
     * <b>修订记录：</b><br>
     * <li>20180814&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Bean
    public Binding bindingExchangeMessageItemDetail(@Qualifier("mq_queue_item_detail") Queue queueMessageNotify, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessageNotify).to(exchange).with(RabbitMqConstant.Queue.QUEUE_ITEM_DETAIL + ".#");
    }

    @Bean
    public Binding bindingExchangeMessageSettle(@Qualifier("mq_queue_settle") Queue queueMessageNotify, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessageNotify).to(exchange).with(RabbitMqConstant.Queue.QUEUE_SETTLE + ".#");
    }

    /**
     * <b>功能描述：</b>退单绑定交换机<br>
     * <b>修订记录：</b><br>
     * <li>20180806&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Bean
    public Binding bindingExchangeMessageRefund(@Qualifier("mq_queue_refund") Queue queueMessageNotify, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessageNotify).to(exchange).with(RabbitMqConstant.Queue.QUEUE_REFUND + ".#");
    }

    @Bean
    public Binding bindingExchangeMessageScanPay(@Qualifier("mq_queue_scan_pay_settle") Queue queueMessageNotify, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessageNotify).to(exchange).with(RabbitMqConstant.Queue.QUEUE_SETTLE_WUU + ".#");
    }

}