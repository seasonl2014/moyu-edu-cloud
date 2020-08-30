package cn.xueden.ucenter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**配置类
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/17
 * @Description:cn.xueden.ucenter.config
 * @version:1.0
 */
@Configuration
@EnableTransactionManagement
@MapperScan("cn.xueden.*.mapper")
public class UcServiceConfig {
}
