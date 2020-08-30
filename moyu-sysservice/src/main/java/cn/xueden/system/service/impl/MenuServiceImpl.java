package cn.xueden.system.service.impl;

import cn.xueden.common.entity.Menu;
import cn.xueden.common.exception.ServiceException;
import cn.xueden.common.utils.MenuTreeBuilder;
import cn.xueden.common.vo.MenuNodeVO;
import cn.xueden.common.vo.MenuVO;
import cn.xueden.system.converter.MenuConverter;
import cn.xueden.system.mapper.MenuMapper;
import cn.xueden.system.service.IMenuService;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**功能描述：菜单模块业务接口实现类
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/19
 * @Description:cn.xueden.system.service.impl
 * @version:1.0
 */
@Service
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 加载菜单树（按钮和菜单）
     * @return
     */
    @Override
    public List<MenuNodeVO> findMenuTree() {

        List<Menu> menus = menuMapper.selectAll();
        List<MenuNodeVO> menuNodeVOS = MenuConverter.converterToALLMenuNodeVO(menus);
        return MenuTreeBuilder.build(menuNodeVOS);
    }

    /**
     * 加载展开项
     * @return
     */
    @Override
    public List<Long> findOpenIds() {
        List<Long> ids = new ArrayList<>();
        List<Menu> menus = menuMapper.selectAll();
        if(!CollectionUtils.isEmpty(menus)){
            for(Menu menu : menus){
                if(menu.getOpen()==1){
                    ids.add(menu.getId());
                }
            }
        }
        return ids;
    }

    /**
     * 添加菜单
     * @param menuVO
     * @return
     */
    @Override
    public Menu add(MenuVO menuVO) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuVO,menu);
        menu.setCreateTime(new Date());
        menu.setModifiedTime(new Date());
        menu.setAvailable(menuVO.isDisabled()?0:1);
        menuMapper.insert(menu);
        return menu;
    }

    /**
     * 编辑菜单
     * @param id
     * @return
     */
    @Override
    public MenuVO edit(Long id) {
        Menu menu = menuMapper.selectByPrimaryKey(id);
        if(menu==null){
            throw  new ServiceException("该编辑菜单不存在");
        }
        return MenuConverter.converterToMenuVO(menu);
    }

    /**
     * 根据菜单ID更新菜单信息
     * @param id
     * @param menuVO
     */
    @Override
    public void update(Long id, MenuVO menuVO) {
        Menu dbmenu = menuMapper.selectByPrimaryKey(id);
        if(dbmenu==null){
            throw new ServiceException("要更新的菜单不存在");
        }

        Menu menu = new Menu();
        BeanUtils.copyProperties(menuVO,menu);
        menu.setId(id);
        menu.setAvailable(menuVO.isDisabled()?0:1);
        menu.setModifiedTime(new Date());
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    /**
     * 删除菜单
     * @param id
     */
    @Override
    public void delete(Long id) {
        menuMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取所有菜单
     * @return
     */
    @Override
    public List<Menu> findAll() {
        return menuMapper.selectAll();
    }
}
