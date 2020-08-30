package cn.xueden.common.enums;

/**功能描述：业务用户类型
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/18
 * @Description:cn.xueden.common.enums
 * @version:1.0
 */
public enum BizUserTypeEnum {
    DEAN("部门主任");
    private String val;

    BizUserTypeEnum(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
