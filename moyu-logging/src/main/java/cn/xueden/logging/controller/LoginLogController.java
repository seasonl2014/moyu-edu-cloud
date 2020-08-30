package cn.xueden.logging.controller;

import cn.xueden.common.bean.ResponseBean;
import cn.xueden.common.vo.LoginLogVO;
import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.UserVO;
import cn.xueden.logging.service.ILoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**功能描述：登入日志模块控制层
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/19
 * @Description:cn.xueden.logging.controller
 * @version:1.0
 */
@Api(tags = "登入日志接口")
@RestController
@RequestMapping("/system/loginLog")
public class LoginLogController {

    @Autowired
    private ILoginLogService loginLogService;

    /**
     * 获取登入日志列表
     * @param pageNum
     * @param pageSize
     * @param loginLogVO
     * @return
     */
    @ApiOperation(value = "登入日志列表",notes = "登入日志列表，模糊查询")
    @GetMapping("/findLoginLogList")
    public ResponseBean findLoginLogList(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize,
                                         LoginLogVO loginLogVO){
        PageVO<LoginLogVO> loginLogList = loginLogService.findLoginLogList(pageNum,pageSize,loginLogVO);
        return ResponseBean.success(loginLogList);
    }

    /**
     * 删除日志
     * @param id
     * @return
     */
    @ApiOperation(value = "删除日志")
    @RequiresPermissions({"loginLog:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseBean delete(@PathVariable Long id){
        loginLogService.delete(id);
        return ResponseBean.success();

    }

    /**
     * 批量删除登入日志
     * @param ids
     * @return
     */
    @ApiOperation("批量删除登入日志")
    @RequiresPermissions({"loginLog:batchDelete"})
    @DeleteMapping("/batchDelete/{ids}")
    public ResponseBean batchDelete(@PathVariable String ids){
        String[] idList = ids.split(",");
        List<Long> list = new ArrayList<>();
        if(idList.length>0){
            for(String s: idList){
                list.add(Long.parseLong(s));
            }
        }
        loginLogService.batchDelete(list);
        return ResponseBean.success();
    }

    /**
     * 登入报表
     * @param userVO
     * @return
     */
    @ApiOperation(value = "登入报表",notes = "用户登入报表")
    @PostMapping("/loginReport")
    public ResponseBean loginReport(UserVO userVO){

        List<Map<String,Object>> mapList = loginLogService.loginReport(userVO);
        Map<String,Object> map=new HashMap<>();
        userVO.setUsername(null);
        List<Map<String,Object>> meList= loginLogService.loginReport(userVO);
        map.put("me",mapList);
        map.put("all",meList);
        return ResponseBean.success(map);
    }
}
