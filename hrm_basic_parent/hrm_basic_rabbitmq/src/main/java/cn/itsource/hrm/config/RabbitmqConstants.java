package cn.itsource.hrm.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class RabbitmqConstants {
    public static final String EXCHANGE_DIRECT_INFORM = "exchange_direct_inform";
    public static final String QUEUE_INFORM_PAGESTATIC = "queue_inform_pageStatic";
}
