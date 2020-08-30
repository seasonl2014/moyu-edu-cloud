package cn.xueden.ucenter.converter;

import cn.hutool.core.collection.CollectionUtil;
import cn.xueden.common.entity.Menu;
import cn.xueden.common.vo.MenuNodeVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**功能描述：菜单转换类
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/18
 * @Description:cn.xueden.ucenter.converter
 * @version:1.0
 */
public class MenuConverter {

    /**
     * 转成menuVO(只包含菜单)List
     * @param menus
     * @return
     */
    public static List<MenuNodeVO> converterToMenuNodeVO(List<Menu> menus){
        //先过滤出用户的菜单
        List<MenuNodeVO> menuNodeVOS = new ArrayList<>();
        if(!CollectionUtil.isEmpty(menus)){
            for(Menu menu: menus){
                if(menu.getType()==0){
                    MenuNodeVO menuNodeVO = new MenuNodeVO();
                    BeanUtils.copyProperties(menu,menuNodeVO);
                    menuNodeVO.setDisabled(menu.getAvailable()==0);
                    menuNodeVOS.add(menuNodeVO);
                }
            }
        }

        return menuNodeVOS;
    }
}
