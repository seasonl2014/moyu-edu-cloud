package cn.xueden.common.bean;

import lombok.Data;

/**功能描述：封装响应的数据结构
 * 定义返回数据使用的状态码
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/15
 * @Description:cn.xueden.common.bean
 * @version:1.0
 */
@Data
public class ResponseBean {

    /**
     * 200：操作成功 -1：操作失败
     */
    private int code;

    //返回信息
    private String msg;

    //返回的数据
    private Object data;

    public ResponseBean() {
    }

    public  ResponseBean(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    //操作失败
    public static ResponseBean error(String message){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setMsg(message);
        responseBean.setCode(-1);
        return responseBean;
    }

    //操作失败
    public static ResponseBean error(int code,String message){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setMsg(message);
        responseBean.setCode(code);
        return responseBean;
    }

    //操作成功
    public static ResponseBean success(Object data){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setMsg("成功");
        responseBean.setCode(200);
        responseBean.setData(data);
        return responseBean;
    }

    //操作成功
    public static ResponseBean success(String message){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setMsg(message);
        responseBean.setCode(200);
        responseBean.setData(null);
        return responseBean;
    }

    //操作成功
    public static ResponseBean success(){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setMsg("Success");
        responseBean.setCode(200);
        responseBean.setData(null);
        return responseBean;
    }



}
