package cn.itsource.hrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FastDfs9003Application {
    public static void main(String[] args) {
        SpringApplication.run(FastDfs9003Application.class,args);
    }
}
