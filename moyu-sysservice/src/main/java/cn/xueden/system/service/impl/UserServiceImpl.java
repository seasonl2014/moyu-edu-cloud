package cn.xueden.system.service.impl;

import cn.xueden.common.bean.JWTUser;
import cn.xueden.common.entity.Department;
import cn.xueden.common.entity.Role;
import cn.xueden.common.entity.User;
import cn.xueden.common.entity.UserRole;
import cn.xueden.common.enums.UserStatusEnum;
import cn.xueden.common.enums.UserTypeEnum;
import cn.xueden.common.exception.ErrorCodeEnum;
import cn.xueden.common.exception.ServiceException;
import cn.xueden.common.utils.JWTUtils;
import cn.xueden.common.utils.MD5Utils;
import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.UserEditVO;
import cn.xueden.common.vo.UserVO;
import cn.xueden.system.converter.UserConverter;
import cn.xueden.system.mapper.DepartmentMapper;
import cn.xueden.system.mapper.RoleMapper;
import cn.xueden.system.mapper.UserMapper;
import cn.xueden.system.mapper.UserRoleMapper;
import cn.xueden.system.service.IUserService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**功能描述：用户管理模块业务接口实现类
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/22
 * @Description:cn.xueden.system.service.impl
 * @version:1.0
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 分页获取用户列表
     * @param pageNum
     * @param pageSize
     * @param userVO
     * @return
     */
    @Override
    public PageVO<UserVO> findUserList(Integer pageNum, Integer pageSize, UserVO userVO) {

        Example o = new Example(User.class);
        String username = userVO.getUsername();
        String nickname = userVO.getNickname();
        Long departmentId = userVO.getDepartmentId();
        Integer sex = userVO.getSex();
        String email = userVO.getEmail();
        Example.Criteria criteria = o.createCriteria();
        if(username!=null&&!"".equals(username)){
            criteria.andLike("username","%"+username+"%");
        }
        if(nickname!=null&&!"".equals(nickname)){
            criteria.andLike("nickname","%"+nickname+"%");
        }

        if(email!=null&&!"".equals(email)){
            criteria.andLike("email","%"+email+"%");
        }

        if(sex!=null){
            criteria.andEqualTo("sex",sex);
        }

        if(departmentId!=null){
            criteria.andEqualTo("departmentId",departmentId);
        }
        criteria.andNotEqualTo("type",0);

        List<User> userList = userMapper.selectByExample(o);

        List<UserVO> userVOS = userConverter.converterToUserVOList(userList);

        PageInfo<User> info = new PageInfo<>(userList);

        return new PageVO<>(info.getTotal(),userVOS);
    }

    /**
     * 添加用户
     * @param userVO
     */
    @Override
    public void add(UserVO userVO) {
       @NotBlank(message = "用户名不能为空") String username = userVO.getUsername();
       @NotNull(message = "部门ID不能为空") Long departmentId = userVO.getDepartmentId();

       Example o = new Example(User.class);
       o.createCriteria().andEqualTo("username",username);
       int i = userMapper.selectCountByExample(o);
       if(i!=0){
           throw new ServiceException("该用户名已经被占用");
       }

        Department department = departmentMapper.selectByPrimaryKey(departmentId);

       if(department==null){
           throw new ServiceException("该部门不存在");
       }

       User user = new User();
        BeanUtils.copyProperties(userVO,user);
        String salt = UUID.randomUUID().toString().substring(0,32);
        user.setPassword(MD5Utils.md5Encryption(user.getPassword(),salt));
        user.setCreateTime(new Date());
        user.setModifiedTime(new Date());
        user.setType(UserTypeEnum.SYSTEM_USER.getTypeCode());//添加的用户默认是普通用户
        user.setStatus(UserStatusEnum.AVAILABLE.getStatusCode());//添加的用户默认是启用
        user.setAvatar("http://java.goodym.cn/touxiang.png");
        userMapper.insert(user);
    }

    /**
     * 编辑用户
     * @param id
     * @return
     */
    @Override
    public UserEditVO edit(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        if(user==null){
            throw new ServiceException("要编辑用户不存在");
        }
        UserEditVO userEditVO = new UserEditVO();
        BeanUtils.copyProperties(user,userEditVO);

        Department department = departmentMapper.selectByPrimaryKey(user.getDepartmentId());
        if(department!=null){
            userEditVO.setDepartmentId(department.getId());
        }else{
            throw new ServiceException("部门不存在");
        }
        return userEditVO;
    }

    /**
     * 更新用户信息
     * @param id
     * @param userVO
     */
    @Override
    public void update(Long id, UserEditVO userVO) {
        User dbUser = userMapper.selectByPrimaryKey(id);
        if(dbUser==null){
            throw new ServiceException("要更新的用户不存在");
        }
        @NotBlank(message = "用户名不能为空") String username = userVO.getUsername();
        @NotNull(message = "部门ID不能为空") Long departmentId = userVO.getDepartmentId();
        Department department = departmentMapper.selectByPrimaryKey(departmentId);
        if(department==null){
            throw new ServiceException("该部门不存在");
        }
        Example o = new Example(User.class);
        o.createCriteria().andEqualTo("username",username);
        List<User> users = userMapper.selectByExample(o);
        if(!CollectionUtils.isEmpty(users)){
            if(!users.get(0).getId().equals(id)){
                throw new ServiceException("该用户名已经被占用");
            }
        }
        User user = new User();
        BeanUtils.copyProperties(userVO,user);
        user.setModifiedTime(new Date());
        user.setId(dbUser.getId());
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 删除用户信息
     * @param id
     * @param token
     */
    @Override
    public void deleteById(Long id, String token) {
        User dbUser = userMapper.selectByPrimaryKey(id);
        if(dbUser==null){
            throw new ServiceException("要删除的用户不存在");
        }

        JWTUser jwtUser = JWTUtils.getJWTUser(token);

        if(dbUser.getId().equals(jwtUser.getId())){
            throw new ServiceException("不能删除当前登入用户");
        }
        userMapper.deleteByPrimaryKey(id);

        //删除对应【用户-角色】记录
        Example o = new Example(UserRole.class);
        o.createCriteria().andEqualTo("userId",id);
        userRoleMapper.deleteByExample(o);

    }

    /**
     * 获取用户已有的角色ID
     * @param id
     * @return
     */
    @Override
    public List<Long> roles(Long id) {
        User dbUser = userMapper.selectByPrimaryKey(id);
        if(dbUser==null){
            throw  new ServiceException("该用户不存在");
        }

        Example o = new Example(UserRole.class);

        o.createCriteria().andEqualTo("userId",dbUser.getId());

        List<UserRole> userRoleList = userRoleMapper.selectByExample(o);

        List<Long> roleIds = new ArrayList<>();

        if(!CollectionUtils.isEmpty(userRoleList)){
            for(UserRole userRole:userRoleList){
                Role role = roleMapper.selectByPrimaryKey(userRole.getRoleId());
                if(role!=null){
                    roleIds.add(role.getId());
                }
            }
        }

        return roleIds;
    }

    /**
     * 分配角色
     * @param id
     * @param rids
     */
    @Override
    public void assignRoles(Long id, Long[] rids) {
        //先清空该用户原有的角色
        User dbUser = userMapper.selectByPrimaryKey(id);
        if(dbUser==null){
            throw new ServiceException("用户不存在");
        }
        //删除之前分配的角色
        Example o = new Example(UserRole.class);
        o.createCriteria().andEqualTo("userId",dbUser.getId());
        userRoleMapper.deleteByExample(o);
        //增加现在分配的角色
        if(rids.length>0){
            for(Long rid:rids){
                Role role = roleMapper.selectByPrimaryKey(rid);
                if(role==null){
                    throw new ServiceException("roleId="+rid+",该角色不存在");
                }

                //判断角色状态
                if(role.getStatus()==0){
                    throw new ServiceException("roleName="+role.getRoleName()+",该角色已禁用");
                }

                UserRole userRole = new UserRole();
                userRole.setUserId(id);
                userRole.setRoleId(rid);
                userRoleMapper.insert(userRole);

            }
        }

    }

    /**
     * 更新用户状态
     * @param id
     * @param status
     * @param token
     */
    @Override
    public void updateStatus(Long id, Boolean status, String token) {
        User dbUser = userMapper.selectByPrimaryKey(id);
        if(dbUser==null){
            throw new ServiceException("要更新状态的用户不存在");
        }

        JWTUser jwtUser = JWTUtils.getJWTUser(token);

        if(dbUser.getId().equals(jwtUser.getId())){
            throw new ServiceException(ErrorCodeEnum.DoNotAllowToDisableTheCurrentUser);
        }else {
            User t = new User();
            t.setId(id);
            t.setStatus(status?UserStatusEnum.DISABLE.getStatusCode():
                    UserStatusEnum.AVAILABLE.getStatusCode());

            userMapper.updateByPrimaryKeySelective(t);
        }

    }

    /**
     * 获取所有用户信息
     * @return
     */
    @Override
    public List<User> findAll() {
        return userMapper.selectAll();
    }
}
