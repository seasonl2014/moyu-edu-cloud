package cn.xueden.logging.service;

import cn.xueden.common.entity.Log;
import cn.xueden.common.vo.LogVO;
import cn.xueden.common.vo.PageVO;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**功能描述：系统操作日志业务接口
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/19
 * @Description:cn.xueden.logging.service
 * @version:1.0
 */
public interface ILogService {

    /**
     * 操作日志列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageVO<LogVO> findLogList(Integer pageNum,Integer pageSize,LogVO logVO);

    /**
     * 删除操作日志
     * @param id
     */
    void delete(Long id);

    /**
     * 批量删除操作日志
     * @param list
     */
    void batchDelete(List<Long> list);

    /**
     * 异步保存操作日志
     * @param log
     */
    @Async("CodeAsyncThreadPool")
    void saveLog(Log log);
}
