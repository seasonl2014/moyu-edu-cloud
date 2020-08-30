package cn.xueden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MoyuSysserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoyuSysserviceApplication.class, args);
    }

}
