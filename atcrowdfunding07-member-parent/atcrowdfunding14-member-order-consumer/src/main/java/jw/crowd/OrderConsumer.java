package jw.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
//@EnableDiscoveryClient // 当前版本可以不写
@SpringBootApplication
@EnableConfigurationProperties
public class OrderConsumer {
    public static void main(String[] args) {
        SpringApplication.run(OrderConsumer.class, args);
    }
}

