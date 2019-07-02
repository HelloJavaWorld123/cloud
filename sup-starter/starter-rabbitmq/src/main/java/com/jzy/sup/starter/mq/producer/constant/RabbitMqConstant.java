package com.jzy.sup.starter.mq.producer.constant;

/**
 * <b>功能：</b>RabbitMqConstant<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class RabbitMqConstant {

    public static class Exchange{
        /**
         * 交换机：总线
         */
        public static final String EXCHANGE_COMMON = "sup-common-bus";

    }

    public static class Queue{

        /**
         * 结算同步队列
         */
        public static final String QUEUE_SETTLE = "sup.settle";

        /**
         * 退单同步队列
         */
        public static final String QUEUE_REFUND = "sup.refund";

        /**
         * 日志队列
         */
        public static final String QUEUE_LOG = "sup.log";

        /**
         * 跑批菜品详情
         */
        public static final String QUEUE_ITEM_DETAIL = "rpt.item.detail";


        /**
         * 结算请求吾享队列
         */
        public static final String QUEUE_SETTLE_WUU = "settle.scan.pay";

        /**
         *  测试
         */
        public static final String QUEUE_SETTLE_TEST = "sup.test";

    }

}
