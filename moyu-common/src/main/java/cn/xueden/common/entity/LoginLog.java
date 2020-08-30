package cn.xueden.common.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**功能描述：登入日志实体类
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/19
 * @Description:cn.xueden.common.entity
 * @version:1.0
 */
@Data
@Table(name = "tb_login_log")
public class LoginLog {

    @Id
    private Long id;

    private String username;

    private Date loginTime;

    private String location;

    private String ip;

    private String userSystem;

    private String userBrowser;
}
