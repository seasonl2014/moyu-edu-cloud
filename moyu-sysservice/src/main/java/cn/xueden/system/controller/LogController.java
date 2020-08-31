package cn.xueden.system.controller;

import cn.xueden.common.entity.Log;

import cn.xueden.logging.service.ILogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther:梁志杰
 * @Date:2020/8/30
 * @Description:cn.xueden.system.controller
 * @version:1.0
 */
@Api(tags = "提供给其他微服务调用接口")
@RestController("SysLogController")
@RequestMapping("/other/log")
public class LogController {

    @Autowired
    private ILogService logService;

    @PostMapping("/add")
    public void add(@RequestBody @Validated Log log){
        logService.saveLog(log);
    }
}
