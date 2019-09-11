package cn.itsource.hrm.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 主站
 * 课程管理
 * 职位管理
 */
@Configuration
public class RabbitmqConfig {
    //there is no property or yml
    @Value("${rabbitmq.queues.routingKey}")
    public static String routingKey;
    public static final String EXCHANGE_DIRECT_INFORM = "exchange_direct_inform";
    public static final String QUEUE_INFORM_PAGESTATIC = "queue_inform_pageStatic";

    /**
     * 交换机配置
     * ExchangeBuilder提供了fanout、direct、topic、header交换机类型的配置
     *
     * @return the exchange
     */
    @Bean(EXCHANGE_DIRECT_INFORM)
    public Exchange exchange_direct_inform() {
    //durable(true)持久化，消息队列重启后交换机仍然存在
        return ExchangeBuilder.directExchange(EXCHANGE_DIRECT_INFORM).durable(true).build();
    }

    //声明队列
    @Bean(QUEUE_INFORM_PAGESTATIC) //交给spring管理的bean的名字可以随便的
    public Queue pageStaticQueue() {
        Queue queue = new Queue(QUEUE_INFORM_PAGESTATIC); //队列名
        return queue;
    }

    //    Qualifier//获取特定名称bean
    @Bean
    public Binding BINDING_QUEUE_INFORM_HRMJOBSITE(@Qualifier(QUEUE_INFORM_PAGESTATIC) Queue queue,
                                              @Qualifier(EXCHANGE_DIRECT_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }


    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }
}
