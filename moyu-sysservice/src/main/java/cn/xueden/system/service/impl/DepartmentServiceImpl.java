package cn.xueden.system.service.impl;

import cn.xueden.common.entity.Department;
import cn.xueden.common.entity.Role;
import cn.xueden.common.entity.User;
import cn.xueden.common.entity.UserRole;
import cn.xueden.common.enums.BizUserTypeEnum;
import cn.xueden.common.enums.UserStatusEnum;
import cn.xueden.common.enums.UserTypeEnum;
import cn.xueden.common.exception.ServiceException;
import cn.xueden.common.vo.DeanVO;
import cn.xueden.common.vo.DepartmentVO;
import cn.xueden.common.vo.PageVO;
import cn.xueden.system.converter.DepartmentConverter;
import cn.xueden.system.mapper.DepartmentMapper;
import cn.xueden.system.mapper.RoleMapper;
import cn.xueden.system.mapper.UserMapper;
import cn.xueden.system.mapper.UserRoleMapper;
import cn.xueden.system.service.IDepartmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**功能描述：部门管理模块业务接口实现类
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/18
 * @Description:cn.xueden.system.service.impl
 * @version:1.0
 */
@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;



    /**
     * 分页获取部门列表
     * @param pageNum
     * @param pageSize
     * @param departmentVO
     * @return
     */
    @Override
    public PageVO<DepartmentVO> findDepartmentList(Integer pageNum, Integer pageSize, DepartmentVO departmentVO) {

        Example o = new Example(Department.class);
        if(departmentVO.getName()!=null&&!"".equals(departmentVO.getName())){
            o.createCriteria().andLike("name","%"+departmentVO.getName()+"%");
        }

        //分页获取部门列表
        PageHelper.startPage(pageNum,pageSize);
        List<Department> departments =departmentMapper.selectByExample(o);
        //转VO
        List<DepartmentVO> departmentVOS = new ArrayList<>();
        if(!CollectionUtils.isEmpty(departments)){
            for(Department department:departments){
                DepartmentVO d = new DepartmentVO();
                BeanUtils.copyProperties(department,d);

                User user = userMapper.selectByPrimaryKey(d.getMgrId());
                d.setMgrName(user.getUsername());

                //统计这个部门有多少人
                Example o1 = new Example(User.class);
                o1.createCriteria().andEqualTo("departmentId",department.getId())
                        .andNotEqualTo("type", UserTypeEnum.SYSTEM_ADMIN.getTypeCode());
                d.setTotal(userMapper.selectCountByExample(o1));
                departmentVOS.add(d);

            }
        }
        PageInfo<Department> info = new PageInfo<>(departments);
        return new PageVO<>(info.getTotal(),departmentVOS);
    }

    /**
     * 查找所有部门主任
     * @return
     */
    @Override
    public List<DeanVO> findDeanList() {
        Example o = new Example(Role.class);
        o.createCriteria().andEqualTo("roleName", BizUserTypeEnum.DEAN.getVal());
        List<Role> roles = roleMapper.selectByExample(o);

        List<DeanVO> list = new ArrayList<>();

        if(!CollectionUtils.isEmpty(roles)){
            Role role = roles.get(0);
            Example o1 = new Example(UserRole.class);

            List<UserRole> userRoleList = userRoleMapper.selectByExample(o1);

            if(!CollectionUtils.isEmpty(userRoleList)){
                //存放所有部门主任的ID
                List<Long> userIds = new ArrayList<>();
                for(UserRole userRole:userRoleList){
                    userIds.add(userRole.getUserId());
                }
                if(userIds.size()>0){
                    for (Long userId:userIds){
                        User user = userMapper.selectByPrimaryKey(userId);
                        //所有可用的
                        if(user!=null&&user.getStatus()== UserStatusEnum.AVAILABLE.getStatusCode()){
                            DeanVO deanVO = new DeanVO();
                            deanVO.setName(user.getUsername());
                            deanVO.setId(user.getId());
                            list.add(deanVO);
                        }
                    }
                }
            }

        }

        return list;
    }

    /**
     * 添加部门
     * @param departmentVO
     */
    @Override
    public void add(DepartmentVO departmentVO) {
     @NotNull(message = "部门主任不能为空") Long mgrId = departmentVO.getMgrId();
        checkMgr(mgrId);
        Department department = new Department();
        BeanUtils.copyProperties(departmentVO,department);
        department.setCreateTime(new Date());
        department.setModifiedTime(new Date());
        departmentMapper.insert(department);
    }

    /**
     * 验证部门主任
     * @param mgrId
     */
    private void checkMgr(@NotNull(message = "部门主任不能为空") Long mgrId){
        User user = userMapper.selectByPrimaryKey(mgrId);
        if(user==null){
            throw new ServiceException("不存在该部门主任");
        }

        List<DeanVO> deanList = findDeanList();

        boolean isMgr = false;
        for(DeanVO deanVO:deanList){
            if(deanVO.getId().equals(user.getId())){
                isMgr = true;
            }
        }

        if(!isMgr){
            throw new ServiceException("该用户已无部门主任身份");
        }
    }

    /**
     * 编辑部门
     * @param id
     * @return
     */
    @Override
    public DepartmentVO edit(Long id) {
        Department department = departmentMapper.selectByPrimaryKey(id);
        if(department==null){
            throw  new ServiceException("编辑部门不存在");
        }
        return DepartmentConverter.converterToDepartmentVO(department);
    }

    /**
     * 更新部门
     * @param id
     * @param departmentVO
     */
    @Override
    public void update(Long id, DepartmentVO departmentVO) {

        Department dbDepartment = departmentMapper.selectByPrimaryKey(id);
        @NotNull(message = "部门主任不能为空") Long mgrId = departmentVO.getMgrId();
        if(dbDepartment==null){
            throw new ServiceException("需要更新的部门不存在");
        }
        checkMgr(mgrId);
        Department department = new Department();
        BeanUtils.copyProperties(departmentVO,department);
        department.setId(id);
        department.setModifiedTime(new Date());
        departmentMapper.updateByPrimaryKeySelective(department);
    }

    /**
     * 删除部门
     * @param id
     */
    @Override
    public void delete(Long id) {
        Department department = departmentMapper.selectByPrimaryKey(id);
        if(department==null){
            throw new ServiceException("要删除的部门不存在");
        }
        departmentMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取所有部门信息
     * @return
     */
    @Override
    public List<Department> findAll() {
        return departmentMapper.selectAll();
    }

    /**
     * 获取所有部门信息
     * @return
     */
    @Override
    public List<DepartmentVO> findAllVO() {
        List<Department> departments = departmentMapper.selectAll();

        List<DepartmentVO> departmentVOS = new ArrayList<>();

        if(!CollectionUtils.isEmpty(departments)){
            for(Department department:departments){
                DepartmentVO d = new DepartmentVO();
                BeanUtils.copyProperties(department,d);
                Example o = new Example(User.class);
                o.createCriteria().andEqualTo("departmentId",department.getId())
                        .andNotEqualTo("type",0);
                d.setTotal(userMapper.selectCountByExample(o));
                departmentVOS.add(d);
            }
        }
        return departmentVOS;
    }
}
