package cn.xueden.logging.mapper;

import cn.xueden.common.entity.LoginLog;
import cn.xueden.common.vo.UserVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/19
 * @Description:cn.xueden.logging.mapper
 * @version:1.0
 */
public interface LoginLogMapper extends Mapper<LoginLog> {

    /**
     * 用户登入报表
     * @param userVO
     * @return
     */
    List<Map<String,Object>> userLoginReport(UserVO userVO);
}
