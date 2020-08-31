package cn.xueden.ucenter.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.xueden.common.bean.ActiveUser;
import cn.xueden.common.client.SystemClient;
import cn.xueden.common.config.jwt.JWTToken;
import cn.xueden.common.entity.*;
import cn.xueden.common.enums.UserTypeEnum;
import cn.xueden.common.exception.ErrorCodeEnum;
import cn.xueden.common.exception.ServiceException;
import cn.xueden.common.utils.JWTUtils;
import cn.xueden.common.utils.MD5Utils;
import cn.xueden.common.utils.MenuTreeBuilder;

import cn.xueden.common.vo.DepartmentVO;
import cn.xueden.common.vo.MenuNodeVO;
import cn.xueden.common.vo.UserInfoVO;


import cn.xueden.ucenter.converter.MenuConverter;
import cn.xueden.ucenter.mapper.*;
import cn.xueden.ucenter.service.IUserService;
import com.mysql.cj.xdevapi.Collection;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**功能描述：用户业务接口实现类
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/17
 * @Description:cn.xueden.ucenter.service.impl
 * @version:1.0
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private SystemClient systemClient;




    /**
     * 根据用户名查询用户信息
     * @param name 用户名
     * @return
     */
    @Override
    public User findUserByName(String name) {
        User t = new User();
        t.setUsername(name);
        return userMapper.selectOne(t);
    }

    @Override
    public List<Role> findRolesById(Long id) {

        User dbUser = userMapper.selectByPrimaryKey(id);

        if(dbUser==null){
            throw new ServiceException("该用户不存在");
        }

        List<Role> roles = new ArrayList<>();
        UserRole userRole = new UserRole();
        userRole.setUserId(dbUser.getId());
        List<UserRole> userRoleList = userRoleMapper.select(userRole);
        List<Long> rids = new ArrayList<>();
        if(!CollectionUtil.isEmpty(userRoleList)){
            for(UserRole ur:userRoleList){
                rids.add(ur.getRoleId());
            }

            if(!CollectionUtil.isEmpty(rids)){
                for(Long rid:rids){
                    Role role = roleMapper.selectByPrimaryKey(rid);
                    if(role!=null){
                        roles.add(role);
                    }
                }
            }
        }

        return roles;
    }

    /**
     * 根据角色列表查询权限列表
     * @param roles
     * @return
     */
    @Override
    public List<Menu> findMenuByRoles(List<Role> roles) {
        List<Menu> menus = new ArrayList<>();
        if(!CollectionUtil.isEmpty(roles)){
            Set<Long> menuIds = new HashSet<>();//存放用户菜单ID
            List<RoleMenu> roleMenus;

            for(Role role:roles){
                //根据角色ID查询权限ID
                Example o =new Example(RoleMenu.class);
                o.createCriteria().andEqualTo("roleId",role.getId());
                roleMenus = roleMenuMapper.selectByExample(o);
                if(!CollectionUtil.isEmpty(roleMenus)){
                    for(RoleMenu roleMenu:roleMenus){
                        menuIds.add(roleMenu.getMenuId());
                    }
                }
            }

            if(!CollectionUtil.isEmpty(menuIds)){
                for(Long menuId:menuIds){
                    //获取该用户的所有菜单
                    Menu menu = menuMapper.selectByPrimaryKey(menuId);
                    if(menu!=null){
                        menus.add(menu);
                    }
                }
            }

        }
        return menus;
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public String login(String username, String password) {
        String token;
        User user = findUserByName(username);
        if(user!=null){
            String salt = user.getSalt();
            //秘钥为盐
            String target = MD5Utils.md5Encryption(password,salt);
            //生成token
            token = JWTUtils.sign(username,target);
            JWTToken jwtToken = new JWTToken(token);
            try {
                SecurityUtils.getSubject().login(jwtToken);
            }catch (AuthenticationException e){
                throw new ServiceException(e.getMessage());
            }
        }else {
            throw new ServiceException(ErrorCodeEnum.USER_ACCOUNT_NOT_FOUND);
        }
        return token;
    }

    /**
     * 用户详情
     * @return
     */
    @Override
    public UserInfoVO info() {
        ActiveUser activeUser = (ActiveUser)SecurityUtils.getSubject().getPrincipal();
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setAvatar(activeUser.getUser().getAvatar());

        userInfoVO.setUsername(activeUser.getUser().getUsername());
        userInfoVO.setUrl(activeUser.getUrls());
        userInfoVO.setNickname(activeUser.getUser().getNickname());
        List<String> roleNames = activeUser.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList());
        userInfoVO.setRoles(roleNames);
        userInfoVO.setPerms(activeUser.getPermissions());
        userInfoVO.setIsAdmin(activeUser.getUser().getType()== UserTypeEnum.SYSTEM_ADMIN.getTypeCode());

        DepartmentVO dept = systemClient.get(activeUser.getUser().getDepartmentId());

         if(dept!=null){
            userInfoVO.setDepartment(dept.getName());
        }


        return userInfoVO;
    }


    /**
     * 加载用户菜单
     * @return
     */
    @Override
    public List<MenuNodeVO> findMenu() {
        List<Menu> menus = null;
        List<MenuNodeVO> menuNodeVOS = new ArrayList<>();

        ActiveUser activeUser = (ActiveUser)SecurityUtils.getSubject().getPrincipal();

        if(activeUser.getUser().getType()==UserTypeEnum.SYSTEM_ADMIN.getTypeCode()){
            //系统超级管理员
            menus = menuMapper.selectAll();
        }else if(activeUser.getUser().getType()==UserTypeEnum.SYSTEM_USER.getTypeCode()){
            //普通系统用户
            menus = activeUser.getMenus();
        }

        if(!CollectionUtil.isEmpty(menus)){
            menuNodeVOS = MenuConverter.converterToMenuNodeVO(menus);
        }
        //构建树形菜单
        return MenuTreeBuilder.build(menuNodeVOS);
    }
}
