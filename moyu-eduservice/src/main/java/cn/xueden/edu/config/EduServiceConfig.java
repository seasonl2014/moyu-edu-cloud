package cn.xueden.edu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Auther:梁志杰
 * @Date:2020/8/30
 * @Description:cn.xueden.edu.config
 * @version:1.0
 */
@EnableTransactionManagement
@Configuration
@MapperScan("cn.xueden.*.mapper")
public class EduServiceConfig {
}
