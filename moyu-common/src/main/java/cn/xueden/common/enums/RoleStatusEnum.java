package cn.xueden.common.enums;

/**功能描述：角色状态
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/20
 * @Description:cn.xueden.common.enums
 * @version:1.0
 */
public enum RoleStatusEnum {
    DISABLE(0),
    AVAILABLE(1);

    private int statusCode;

     RoleStatusEnum(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
