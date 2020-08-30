package cn.xueden.ucenter.config.shiro;

import cn.hutool.core.collection.CollectionUtil;
import cn.xueden.common.bean.ActiveUser;
import cn.xueden.common.config.jwt.JWTToken;
import cn.xueden.common.entity.Menu;
import cn.xueden.common.entity.Role;
import cn.xueden.common.entity.User;
import cn.xueden.common.utils.JWTUtils;
import cn.xueden.ucenter.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**功能描述：自定义用户认证和授权
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/17
 * @Description:cn.xueden.ucenter.config.shiro
 * @version:1.0
 */
@Service
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        ActiveUser activeUser = (ActiveUser)SecurityUtils.getSubject().getPrincipal();
        if(activeUser.getUser().getType()==0){
            authorizationInfo.addStringPermission("*:*");
        }else{
            List<String> permissions = new ArrayList<>(activeUser.getPermissions());

            List<Role> roleList = activeUser.getRoles();

            //授权角色
            if(!CollectionUtils.isEmpty(roleList)){
                for(Role role:roleList){
                    authorizationInfo.addRole(role.getRoleName());
                }
            }

            //授权权限
            if(!CollectionUtils.isEmpty(permissions)){
                for(String permission:permissions){
                    if(permission!=null&&!"".equals(permission)){
                        authorizationInfo.addStringPermission(permission);
                    }
                }
            }

            return authorizationInfo;
        }

        return null;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String)auth.getCredentials();
        //解密token得到用户，用于和数据库进行比对
        String username = JWTUtils.getUsername(token);
        if(username==null){
            throw new AuthenticationException("token错误，请重新登入！");
        }

        User userBean = userService.findUserByName(username);

        if(userBean==null){
            throw new AccountException("账号不存在！");
        }

        if(!JWTUtils.verify(token,username,userBean.getPassword())){
            throw new CredentialsException("密码错误!");
        }

        if(userBean.getStatus()==0){
            throw new LockedAccountException("账号已被锁定!");
        }

        //如果验证通过，获取用户的角色
        List<Role> roles = userService.findRolesById(userBean.getId());

        //查询用户所有菜单(包含按钮)
        List<Menu> menus = userService.findMenuByRoles(roles);
        Set<String> urls=new HashSet<>();
        Set<String> perms=new HashSet<>();
        if(!CollectionUtil.isEmpty(menus)){
            for(Menu menu:menus){
                String url = menu.getUrl();
                String per = menu.getPerms();
                if(menu.getType()==0&&!StringUtils.isEmpty(url)){
                    urls.add(url);
                }
                if(menu.getType()==1&&!StringUtils.isEmpty(per)){
                    perms.add(menu.getPerms());
                }
            }
        }

        //过滤出url和用户权限
        ActiveUser activeUser = new ActiveUser();
        activeUser.setRoles(roles);
        activeUser.setMenus(menus);
        activeUser.setUrls(urls);
        activeUser.setPermissions(perms);
        activeUser.setUser(userBean);
        return new SimpleAuthenticationInfo(activeUser,token,getName());
    }
}
