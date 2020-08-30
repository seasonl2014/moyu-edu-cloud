package cn.xueden.system.service;

import cn.xueden.common.entity.Role;
import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.RoleVO;

import java.util.List;

/**功能描述：角色管理模块业务接口
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/20
 * @Description:cn.xueden.system.service
 * @version:1.0
 */
public interface IRoleService {

    /**
     * 角色列表
     * @param pageNum
     * @param pageSize
     * @param roleVO
     * @return
     */
    PageVO<RoleVO> findRoleList(Integer pageNum,Integer pageSize,RoleVO roleVO);

    /**
     * 更新角色状态
     * @param id
     * @param status
     */
    void updateStatus(Long id,Boolean status);

    /**
     * 添加角色
     * @param roleVO
     */
    void add(RoleVO roleVO);

    /**
     * 编辑角色
     * @param id
     * @return
     */
    RoleVO edit(Long id);

    /**
     * 更新角色
     * @param id
     * @param roleVO
     */
    void update(Long id,RoleVO roleVO);

    /**
     * 查询角色拥有的菜单权限ID
     * @param id
     * @return
     */
    List<Long> findMenuIdsByRoleId(Long id);

    /**
     * 角色授权
     * @param id
     * @param mids
     */
    void authority(Long id,Long[] mids);

    /**
     * 删除角色
     * @param id
     */
    void deleteById(Long id);

    /**
     * 获取所有角色
     * @return
     */
    List<Role> findAll();
}
