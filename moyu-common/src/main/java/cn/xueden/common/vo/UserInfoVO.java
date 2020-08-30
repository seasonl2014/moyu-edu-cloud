package cn.xueden.common.vo;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**功能描述：用户视图对象
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/17
 * @Description:cn.xueden.common.vo
 * @version:1.0
 */
@Data
public class UserInfoVO {

    private String username;

    private String nickname;

    private String avatar;

    private Set<String> url;

    private Set<String> perms;

    private List<String> roles;

    private String department;

    private Boolean isAdmin=false;
}
