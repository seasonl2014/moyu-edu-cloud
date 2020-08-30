package cn.xueden.ekservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MoyuEkserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoyuEkserviceApplication.class, args);
    }

}
