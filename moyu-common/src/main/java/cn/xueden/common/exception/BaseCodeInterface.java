package cn.xueden.common.exception;

/**功能描述：自定义的错误描述枚举类需实现该接口
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/15
 * @Description:cn.xueden.common.exception
 * @version:1.0
 */
public interface BaseCodeInterface {

    /**
     * 错误码
     * @return
     */
    int getResultCode();

    /**
     * 错误描述
     * @return
     */
    String getResultMsg();
}
