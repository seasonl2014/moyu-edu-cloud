package cn.xueden.logging.controller;

import cn.xueden.common.bean.ResponseBean;
import cn.xueden.common.vo.LogVO;
import cn.xueden.common.vo.PageVO;
import cn.xueden.logging.service.ILogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**功能描述：系统操作日志控制层
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/19
 * @Description:cn.xueden.logging.controller
 * @version:1.0
 */
@Api(tags = "系统操作日志接口")
@RestController
@RequestMapping("/system/log")
public class LogController {

    @Autowired
    private ILogService logService;

    @ApiOperation(value = "系统操作日志列表",notes = "获取系统操作日志类别，模糊查询")
    @GetMapping("/findLogList")
    public ResponseBean findLogList(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                    @RequestParam(value = "pageSize",defaultValue = "15")Integer pageSize,
                                    LogVO logVO){
        PageVO<LogVO> logList = logService.findLogList(pageNum,pageSize,logVO);
        return ResponseBean.success(logList);
    }

    /**
     * 删除操作日志
     * @param id
     * @return
     */
    @ApiOperation(value ="删除日志" )
    @RequiresPermissions({"logging:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseBean delete(@PathVariable Long id){
        logService.delete(id);
        return ResponseBean.success("删除系统操作日志成功");
    }

    /**
     * 批量删除操作日志
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除操作日志")
    @RequiresPermissions({"logging:batchDelete"})
    @DeleteMapping("/batchDelete/{ids}")
    public ResponseBean batchDelete(@PathVariable String ids){
        String[] idList = ids.split(",");
        List<Long> list = new ArrayList<>();

        if(idList.length>0){
            for(String s:idList){
                list.add(Long.parseLong(s));
            }
        }
        logService.batchDelete(list);
        return ResponseBean.success("批量删除操作日志成功");
    }
}
