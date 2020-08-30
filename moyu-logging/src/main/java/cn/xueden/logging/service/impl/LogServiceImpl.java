package cn.xueden.logging.service.impl;

import cn.xueden.common.entity.Log;
import cn.xueden.common.vo.LogVO;
import cn.xueden.common.vo.PageVO;
import cn.xueden.logging.mapper.LogMapper;
import cn.xueden.logging.service.ILogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**功能描述：系统操作日志业务接口实现类
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/19
 * @Description:cn.xueden.logging.service.impl
 * @version:1.0
 */
@Service
public class LogServiceImpl implements ILogService {

    @Autowired
    private LogMapper logMapper;

    /**
     * 分页获取操作日志列表
     * @param pageNum
     * @param pageSize
     * @param logVO
     * @return
     */
    @Override
    public PageVO<LogVO> findLogList(Integer pageNum, Integer pageSize, LogVO logVO) {
        Example o = new Example(Log.class);
        Example.Criteria criteria = o.createCriteria();
        o.setOrderByClause("create_time desc");

        if(logVO.getLocation()!=null&&!"".equals(logVO.getLocation())){
            criteria.andLike("location","%"+logVO.getLocation()+"%");
        }

        if(logVO.getIp()!=null&&!"".equals(logVO.getIp())){
            criteria.andLike("ip","%"+logVO.getIp()+"%");
        }

        if(logVO.getUsername()!=null&&!"".equals(logVO.getUsername())){
            criteria.andLike("username","%"+logVO.getUsername()+"%");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Log> logList = logMapper.selectByExample(o);

        List<LogVO> logVOS = new ArrayList<>();

        if(!CollectionUtils.isEmpty(logList)){
           for(Log log:logList){
               LogVO logVO1 = new LogVO();
               BeanUtils.copyProperties(log,logVO1);
               logVOS.add(logVO1);
           }
        }
        PageInfo<Log> info = new PageInfo<>(logList);
        return new PageVO<>(info.getTotal(),logVOS);
    }

    /**
     * 删除操作日志
     * @param id
     */
    @Override
    public void delete(Long id) {
        logMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除操作日志
     * @param list
     */
    @Override
    public void batchDelete(List<Long> list) {
        for(Long id:list){
            delete(id);
        }
    }

    /**
     * 保存操作日志
     * @param log
     */
    @Override
    public void saveLog(Log log) {
        logMapper.insert(log);
    }
}
