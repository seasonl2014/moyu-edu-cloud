package cn.xueden.logging.service;

import cn.xueden.common.vo.LoginLogVO;
import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**功能描述：登入日志业务接口
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/19
 * @Description:cn.xueden.logging.service
 * @version:1.0
 */
public interface ILoginLogService {

    /**
     * 登入日志列表
     * @param pageNum
     * @param PageSize
     * @param loginLogVO
     * @return
     */
    PageVO<LoginLogVO> findLoginLogList(Integer pageNum,Integer PageSize,LoginLogVO loginLogVO);

    /**
     * 删除登入日志
     * @param id
     */
    void delete(Long id);

    /**
     * 批量删除登入日志
     * @param list
     */
    void batchDelete(List<Long> list);

    /**
     * 添加用户登入日志
     * @param request
     */
    void add(HttpServletRequest request);

    /**
     * 用户登入报表
     * @param userVO
     * @return
     */
    List<Map<String,Object>> loginReport(UserVO userVO);
}
