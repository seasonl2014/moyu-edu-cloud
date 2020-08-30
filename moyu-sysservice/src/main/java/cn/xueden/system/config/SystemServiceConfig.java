package cn.xueden.system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**功能描述:配置类
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/18
 * @Description:cn.xueden.system.config
 * @version:1.0
 */
@EnableTransactionManagement
@Configuration
@MapperScan("cn.xueden.*.mapper")
public class SystemServiceConfig {
}
