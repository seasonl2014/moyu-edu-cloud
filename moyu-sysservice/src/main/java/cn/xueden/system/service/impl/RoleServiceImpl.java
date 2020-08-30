package cn.xueden.system.service.impl;

import cn.xueden.common.entity.Menu;
import cn.xueden.common.entity.Role;
import cn.xueden.common.entity.RoleMenu;
import cn.xueden.common.enums.RoleStatusEnum;
import cn.xueden.common.exception.ServiceException;
import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.RoleVO;
import cn.xueden.system.converter.RoleConverter;
import cn.xueden.system.mapper.MenuMapper;
import cn.xueden.system.mapper.RoleMapper;
import cn.xueden.system.mapper.RoleMenuMapper;
import cn.xueden.system.service.IRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**功能描述：角色管理模块业务接口实现类
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/20
 * @Description:cn.xueden.system.service.impl
 * @version:1.0
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 角色列表
     * @param pageNum
     * @param pageSize
     * @param roleVO
     * @return
     */
    @Override
    public PageVO<RoleVO> findRoleList(Integer pageNum, Integer pageSize, RoleVO roleVO) {
        Example o = new Example(Role.class);
        String roleName = roleVO.getRoleName();
        if(roleName!=null&&!"".equals(roleName)){
            o.createCriteria().andLike("roleName","%"+roleName+"%");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Role> roles = roleMapper.selectByExample(o);
        List<RoleVO> roleVOS = RoleConverter.converterToRoleVOList(roles);
        PageInfo<Role> info = new PageInfo<>(roles);
        return new PageVO<>(info.getTotal(),roleVOS);
    }

    /**
     * 更新角色状态
     * @param id
     * @param status
     */
    @Override
    public void updateStatus(Long id, Boolean status) {
        Role dbrole = roleMapper.selectByPrimaryKey(id);
        if(dbrole==null){
            throw new ServiceException("角色不存在");
        }
        Role role = new Role();
        role.setId(id);
        role.setStatus(status?RoleStatusEnum.DISABLE.getStatusCode():
                RoleStatusEnum.AVAILABLE.getStatusCode());
        roleMapper.updateByPrimaryKeySelective(role);

    }

    /**
     * 添加角色
     * @param roleVO
     */
    @Override
    public void add(RoleVO roleVO) {
        @NotBlank(message = "角色名称不能为空") String roleName = roleVO.getRoleName();
        Example o = new Example(Role.class);
        o.createCriteria().andEqualTo("roleName",roleName);
        int i = roleMapper.selectCountByExample(o);
        if(i!=0){
            throw  new ServiceException("该角色已经被占用");
        }
        Role role = new Role();
        BeanUtils.copyProperties(roleVO,role);
        role.setCreateTime(new Date());
        role.setModifiedTime(new Date());
        role.setStatus(RoleStatusEnum.AVAILABLE.getStatusCode());//默认启用添加的角色
        roleMapper.insert(role);
    }

    /**
     * 编辑角色
     * @param id
     * @return
     */
    @Override
    public RoleVO edit(Long id) {
        Role role = roleMapper.selectByPrimaryKey(id);
        if(role==null){
            throw new ServiceException("编辑角色不存在");
        }
        RoleVO roleVO = new RoleVO();
        BeanUtils.copyProperties(role,roleVO);
        return roleVO;
    }

    /**
     * 更新就是信息
     * @param id
     * @param roleVO
     */
    @Override
    public void update(Long id, RoleVO roleVO) {
       @NotBlank(message = "角色名必填") String roleName = roleVO.getRoleName();

       Role dbrole = roleMapper.selectByPrimaryKey(id);

       if(dbrole==null){
           throw new ServiceException("要更新的角色不存在");
       }

       Example o = new Example(Role.class);

       o.createCriteria().andEqualTo("roleName",roleName);

       List<Role> roles = roleMapper.selectByExample(o);

       if(!CollectionUtils.isEmpty(roles)){
           Role role = roles.get(0);
           if(!role.getId().equals(id)){
               throw  new ServiceException("角色名称已被占用");
           }
       }

       Role role = new Role();
        BeanUtils.copyProperties(roleVO,role);
        role.setModifiedTime(new Date());
        roleMapper.updateByPrimaryKeySelective(role);
    }

    /**
     * 获取角色已有的菜单ID
     * @param id
     * @return
     */
    @Override
    public List<Long> findMenuIdsByRoleId(Long id) {
        Role role = roleMapper.selectByPrimaryKey(id);
        if(role==null){
            throw new ServiceException("该角色不存在");
        }
        List<Long> ids = new ArrayList<>();
        Example o = new Example(RoleMenu.class);
        o.createCriteria().andEqualTo("roleId",id);
        List<RoleMenu> roleMenus = roleMenuMapper.selectByExample(o);
        if(!CollectionUtils.isEmpty(roleMenus)){
           for(RoleMenu roleMenu:roleMenus){
               ids.add(roleMenu.getMenuId());
           }
        }
        return ids;
    }

    /**
     * 角色授权
     * @param id
     * @param mids
     */
    @Override
    public void authority(Long id, Long[] mids) {
        Role role = roleMapper.selectByPrimaryKey(id);
        if(role==null){
            throw new ServiceException("该角色不存在");
        }
        //先删除该角色原来的权限
        Example o = new Example(RoleMenu.class);
        o.createCriteria().andEqualTo("roleId",id);
        roleMenuMapper.deleteByExample(o);
        //增加现在分配的角色
        if(mids.length>0){
            for(Long mid:mids){
                Menu menu = menuMapper.selectByPrimaryKey(mid);
                if(menu==null){
                    throw new ServiceException("menuId"+mid+",菜单不存在");
                }else {
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setRoleId(id);
                    roleMenu.setMenuId(mid);
                    roleMenuMapper.insert(roleMenu);

                }
            }
        }
    }

    /**
     * 删除角色
     * @param id
     */
    @Override
    public void deleteById(Long id) {

        Role role = roleMapper.selectByPrimaryKey(id);
        if(role==null){
            throw new ServiceException("要删除的角色不存在");
        }
        roleMapper.deleteByPrimaryKey(id);

        //删除对应的【角色-菜单】记录
        Example o = new Example(RoleMenu.class);
        o.createCriteria().andEqualTo("roleId",id);
        roleMenuMapper.deleteByExample(o);
    }

    /**
     * 获取所有角色
     * @return
     */
    @Override
    public List<Role> findAll() {
        return roleMapper.selectAll();
    }
}
