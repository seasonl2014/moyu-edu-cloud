package cn.xueden.common.entity;

import lombok.Data;

import javax.persistence.Table;

/**功能描述：角色与菜单关系实体类
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/17
 * @Description:cn.xueden.common.entity
 * @version:1.0
 */
@Data
@Table(name = "tb_role_menu")
public class RoleMenu {

    private Long roleId;

    private Long menuId;
}
