package cn.xueden.system.converter;

import cn.xueden.common.entity.Menu;
import cn.xueden.common.vo.MenuNodeVO;
import cn.xueden.common.vo.MenuVO;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**功能描述：菜单转换
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/19
 * @Description:cn.xueden.system.converter
 * @version:1.0
 */
public class MenuConverter {

    /**
     * 转换menuVO(菜单和按钮)
     * @param menus
     * @return
     */
    public static List<MenuNodeVO> converterToALLMenuNodeVO(List<Menu> menus){
        //先过滤出用户的菜单
        List<MenuNodeVO> menuNodeVOS = new ArrayList<>();

        if(!CollectionUtils.isEmpty(menus)){
            for (Menu menu:menus){
                MenuNodeVO menuNodeVO = new MenuNodeVO();
                BeanUtils.copyProperties(menu,menuNodeVO);
                menuNodeVO.setDisabled(menu.getAvailable()==0);
                menuNodeVOS.add(menuNodeVO);
            }
        }
        return menuNodeVOS;
    }

    /**
     * 转换menuVO（菜单和按钮）
     * @param menu
     * @return
     */
    public static MenuVO converterToMenuVO(Menu menu){
        MenuVO menuVO = new MenuVO();
        if(menu!=null){
            BeanUtils.copyProperties(menu,menuVO);
            menuVO.setDisabled(menu.getAvailable()==0);
        }
        return menuVO;
    }
}
