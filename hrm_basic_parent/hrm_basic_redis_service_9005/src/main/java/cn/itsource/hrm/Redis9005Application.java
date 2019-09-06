package cn.itsource.hrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Redis9005Application {
    public static void main(String[] args) {
        SpringApplication.run(Redis9005Application.class,args);
    }
}
