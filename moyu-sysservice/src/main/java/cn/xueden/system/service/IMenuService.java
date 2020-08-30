package cn.xueden.system.service;

import cn.xueden.common.entity.Menu;
import cn.xueden.common.vo.MenuNodeVO;
import cn.xueden.common.vo.MenuVO;

import java.util.List;

/**功能描述：菜单模块业务接口
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/19
 * @Description:cn.xueden.system.service
 * @version:1.0
 */
public interface IMenuService {

    /**
     * 获取菜单树
     * @return
     */
    List<MenuNodeVO> findMenuTree();

    /**
     * 获取展开菜单的ID
     * @return
     */
    List<Long> findOpenIds();

    /**
     * 添加菜单
     * @param menuVO
     * @return
     */
    Menu add(MenuVO menuVO);

    /**
     * 编辑菜单
     * @param id
     * @return
     */
    MenuVO edit(Long id);

    /**
     * 更新菜单
     * @param id
     * @param menuVO
     */
    void update(Long id,MenuVO menuVO);

    /**
     * 删除菜单
     * @param id
     */
    void delete(Long id);

    /**
     * 获取所有菜单
     * @return
     */
    List<Menu> findAll();
}
