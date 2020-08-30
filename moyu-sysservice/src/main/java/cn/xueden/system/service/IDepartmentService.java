package cn.xueden.system.service;

import cn.xueden.common.entity.Department;
import cn.xueden.common.vo.DeanVO;
import cn.xueden.common.vo.DepartmentVO;
import cn.xueden.common.vo.PageVO;

import java.util.List;

/**功能描述：部门管理模块业务接口
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/18
 * @Description:cn.xueden.system.service
 * @version:1.0
 */
public interface IDepartmentService {

    /**
     * 部门列表
     * @param pageNum
     * @param pageSize
     * @param departmentVO
     * @return
     */
    PageVO<DepartmentVO> findDepartmentList(Integer pageNum,Integer pageSize,DepartmentVO departmentVO);

    /**
     * 查找所有部门主任
     * @return
     */
    List<DeanVO> findDeanList();

    /**
     * 添加部门
     * @param departmentVO
     */
    void add(DepartmentVO departmentVO);

    /**
     * 编辑部门
     * @param id
     * @return
     */
    DepartmentVO edit(Long id);

    /**
     * 更新部门
     * @param id
     * @param departmentVO
     */
    void update(Long id,DepartmentVO departmentVO);

    /**
     * 删除不们
     * @param id
     */
    void delete(Long id);

    /**
     * 获取全部门信息
     * @return
     */
    List<Department> findAll();

    /**
     * 获取全部门信息
     * @return
     */
    List<DepartmentVO> findAllVO();
}
