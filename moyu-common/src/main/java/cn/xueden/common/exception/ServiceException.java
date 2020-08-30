package cn.xueden.common.exception;

import cn.xueden.common.exception.BaseCodeInterface;
import lombok.Data;

/**功能描述：业务异常类
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/15
 * @Description:cn.xueden.common
 * @version:1.0
 */
@Data
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    protected int errorCode;

    /**
     * 错误信息
     */
    protected String errorMsg;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
        this.errorMsg=message;
    }

    public ServiceException(BaseCodeInterface errorInfoInterface) {
        this.errorCode = errorInfoInterface.getResultCode();
        this.errorMsg = errorInfoInterface.getResultMsg();
    }

    public ServiceException(BaseCodeInterface errorInfoInterface,String message) {
        this.errorCode = errorInfoInterface.getResultCode();
        this.errorMsg = message;
    }
}
