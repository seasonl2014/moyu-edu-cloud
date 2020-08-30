package cn.xueden.logging.service.impl;

import cn.xueden.common.bean.ActiveUser;
import cn.xueden.common.entity.LoginLog;
import cn.xueden.common.entity.User;
import cn.xueden.common.utils.AddressUtil;
import cn.xueden.common.utils.IPUtil;
import cn.xueden.common.vo.LoginLogVO;
import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.UserVO;
import cn.xueden.logging.mapper.LoginLogMapper;
import cn.xueden.logging.service.ILoginLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**功能描述：登入日志业务接口实现类
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/19
 * @Description:cn.xueden.logging.service.impl
 * @version:1.0
 */
@Service
public class LoginLogServiceImpl implements ILoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    /**
     * 分页获取登入日志列表
     * @param pageNum
     * @param PageSize
     * @param loginLogVO
     * @return
     */
    @Override
    public PageVO<LoginLogVO> findLoginLogList(Integer pageNum, Integer pageSize, LoginLogVO loginLogVO) {
        Example o = new Example(LoginLog.class);
        Example.Criteria criteria = o.createCriteria();
        o.setOrderByClause("login_time desc");
        if(loginLogVO.getIp()!=null&&!"".equals(loginLogVO.getIp())){
            criteria.andLike("ip","%"+loginLogVO.getIp()+"%");
        }

        if(loginLogVO.getLocation()!=null&&!"".equals(loginLogVO.getLocation())){
            criteria.andLike("location","%"+loginLogVO.getLocation()+"%");
        }

        if(loginLogVO.getUsername()!=null&&!"".equals(loginLogVO.getUsername())){
            criteria.andLike("username","%"+loginLogVO.getUsername()+"%");
        }

        PageHelper.startPage(pageNum,pageSize);
        List<LoginLog> loginLogs = loginLogMapper.selectByExample(o);
        List<LoginLogVO> loginLogVOS = new ArrayList<>();

        if(!CollectionUtils.isEmpty(loginLogs)){
            for(LoginLog loginLog:loginLogs){
                LoginLogVO logVO = new LoginLogVO();
                BeanUtils.copyProperties(loginLog,logVO);
                loginLogVOS.add(logVO);
            }
        }
        PageInfo<LoginLog> info = new PageInfo<>(loginLogs);
        return new PageVO<>(info.getTotal(),loginLogVOS);
    }

    /**
     * 删除登入日志
     * @param id
     */
    @Override
    public void delete(Long id) {
        loginLogMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除登入日志
     * @param list
     */
    @Override
    public void batchDelete(List<Long> list) {
        for (Long id:list){
            delete(id);
        }
    }


    /**
     * 新增登入日志
     * @param request
     */
    @Override
    public void add(HttpServletRequest request) {
        loginLogMapper.insert(createLoginLog(request));
    }

    /**
     * 创建登入日志
     * @param request
     * @return
     */
    public static LoginLog createLoginLog(HttpServletRequest request){
        ActiveUser activeUser = (ActiveUser)SecurityUtils.getSubject().getPrincipal();
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(activeUser.getUser().getUsername());
        loginLog.setIp(IPUtil.getIpAddr(request));
        loginLog.setLocation(AddressUtil.getCityInfo(IPUtil.getIpAddr(request)));

        //获取客户端操作系统
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        Browser browser = userAgent.getBrowser();
        OperatingSystem os = userAgent.getOperatingSystem();
        loginLog.setUserSystem(os.getName());
        loginLog.setUserBrowser(browser.getName());
        loginLog.setLoginTime(new Date());
        return loginLog;
    }

    /**
     * 用户登入报表
     * @param userVO
     * @return
     */
    @Override
    public List<Map<String, Object>> loginReport(UserVO userVO) {
        return loginLogMapper.userLoginReport(userVO);
    }
}
