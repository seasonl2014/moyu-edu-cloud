package cn.xueden;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = { DruidDataSourceAutoConfigure.class, DataSourceTransactionManagerAutoConfiguration.class })
@EnableEurekaClient
@EnableFeignClients
public class VidserviceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(VidserviceApplication.class);
    }
}
