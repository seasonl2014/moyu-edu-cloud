package cn.xueden.common.bean;

/**
 * @Auther:梁志杰
 * @Date:2020/8/30
 * @Description:cn.xueden.common.bean
 * @version:1.0
 */
public interface ResultCode {

    //成功状态码
    int SUCCESS = 20000;
    //失败状态码
    int ERROR = 20001;
    //没有操作权限状态码
    int AUTH = 30000;

}
