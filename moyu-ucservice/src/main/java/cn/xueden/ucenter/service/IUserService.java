package cn.xueden.ucenter.service;

import cn.xueden.common.entity.Menu;
import cn.xueden.common.entity.Role;
import cn.xueden.common.entity.User;
import cn.xueden.common.vo.MenuNodeVO;
import cn.xueden.common.vo.UserInfoVO;

import java.util.List;

/**功能描述：用户业务接口
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/17
 * @Description:cn.xueden.ucenter.service
 * @version:1.0
 */
public interface IUserService {

    /**
     * 根据用户名查询用户信息
     * @param name 用户名
     * @return
     */
    User findUserByName(String name);

    /**
     * 根据用户ID获取用户的角色列表
     * @param id
     * @return
     */
    List<Role> findRolesById(Long id);

    //根据就是角色列表获取菜单列表
    List<Menu> findMenuByRoles(List<Role> roles);

    //用户登录
    String login(String username,String password);

    //获取当前用户信息
    UserInfoVO info();

    //加载用户菜单
    List<MenuNodeVO> findMenu();
}
