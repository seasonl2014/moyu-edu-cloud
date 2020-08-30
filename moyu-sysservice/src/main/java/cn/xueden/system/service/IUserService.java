package cn.xueden.system.service;

import cn.xueden.common.entity.User;
import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.UserEditVO;
import cn.xueden.common.vo.UserVO;

import java.util.List;

/**功能描述：用户管理模块业务接口
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/22
 * @Description:cn.xueden.system.service
 * @version:1.0
 */
public interface IUserService {

    /**
     * 分页获取用户列表
     * @param pageNum
     * @param pageSize
     * @param userVO
     * @return
     */
    PageVO<UserVO> findUserList(Integer pageNum,Integer pageSize,UserVO userVO);

    /**
     * 添加用户
     * @param userVO
     */
    void add(UserVO userVO);

    /**
     * 编辑用户
     * @param id
     * @return
     */
    UserEditVO edit(Long id);

    /**
     * 更新用户
     * @param id
     * @param userEditVO
     */
    void update(Long id,UserEditVO userEditVO);

    /**
     * 删除用户
     * @param id
     * @param token
     */
    void deleteById(Long id,String token);

    /**
     * 获取用户已有的角色Id
     * @param id
     * @return
     */
    List<Long> roles(Long id);

    /**
     * 分配角色
     * @param id
     * @param rids
     */
    void assignRoles(Long id,Long[] rids);

    /**
     * 更新用户状态
     * @param id
     * @param status
     * @param token
     */
    void updateStatus(Long id,Boolean status,String token);

    /**
     * 获取所有用户信息
     * @return
     */
    List<User> findAll();

}
