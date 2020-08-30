package cn.xueden.common.vo;

import lombok.Data;

/**功能描述：转成前端需要的角色Item
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/22
 * @Description:cn.xueden.common.vo
 * @version:1.0
 */
@Data
public class RoleTransferItemVO {

    private Long key;

    private String label;

    private boolean disabled;
}
