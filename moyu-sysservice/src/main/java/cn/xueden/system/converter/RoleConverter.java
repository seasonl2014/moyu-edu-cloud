package cn.xueden.system.converter;

import cn.xueden.common.entity.Role;
import cn.xueden.common.vo.RoleTransferItemVO;
import cn.xueden.common.vo.RoleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**角色转换类
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/20
 * @Description:cn.xueden.system.converter
 * @version:1.0
 */
public class RoleConverter {

    /**
     * 实体角色列表转角色视图列表
     * @param roles
     * @return
     */
    public static List<RoleVO> converterToRoleVOList(List<Role> roles){
        List<RoleVO> roleVOS = new ArrayList<>();
        if(!CollectionUtils.isEmpty(roles)){
            for (Role role:roles){
                RoleVO roleVO = new RoleVO();
                BeanUtils.copyProperties(role,roleVO);
                roleVO.setStatus(role.getStatus() == 0);
                roleVOS.add(roleVO);
            }
        }
        return roleVOS;
    }

    /**
     * 转成前端需要的角色Item
     * @param list
     * @return
     */
    public static List<RoleTransferItemVO> converterToRoleTransferItem(List<Role> list){
        List<RoleTransferItemVO> itemVOList = new ArrayList<>();

        if(!CollectionUtils.isEmpty(list)){
            for(Role role:list){
                RoleTransferItemVO item = new RoleTransferItemVO();
                item.setLabel(role.getRoleName());
                item.setDisabled(role.getStatus()==0);
                item.setKey(role.getId());
                itemVOList.add(item);
            }
        }

        return itemVOList;
    }
}
