package cn.xueden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Auther:梁志杰
 * @Date:2020/8/30
 * @Description:cn.xueden
 * @version:1.0
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class EduServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduServiceApplication.class,args);
    }
}
