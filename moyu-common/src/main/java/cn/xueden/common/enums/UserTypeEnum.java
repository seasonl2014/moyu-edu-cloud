package cn.xueden.common.enums;

/**功能描述：用户类型
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/17
 * @Description:cn.xueden.common.enums
 * @version:1.0
 */
public enum UserTypeEnum {
    SYSTEM_ADMIN(0),//系统管理员admin
    SYSTEM_USER(1);//系统普通用户

    private int typeCode;

    UserTypeEnum(int typeCode) {
        this.typeCode = typeCode;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(int typeCode) {
        this.typeCode = typeCode;
    }
}
