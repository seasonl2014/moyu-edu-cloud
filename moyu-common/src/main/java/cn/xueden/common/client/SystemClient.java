package cn.xueden.common.client;




import cn.xueden.common.entity.Log;
import cn.xueden.common.vo.DepartmentVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Auther:梁志杰
 * @Date:2020/8/30
 * @Description:cn.xueden.logging.client
 * @version:1.0
 */
@FeignClient(name = "moyu-sysservice") //找到注册中心里的moyu-sysservice微服务
@Component
public interface SystemClient {


    /**
     * 添加日志
     *
     * @param log
     * @return
     */
    @PostMapping("/other/log/add")
    public void add(@RequestBody Log log);

    /**
     * 根据Id获取部门信息
     * @param id
     * @return
     */
    @GetMapping("/system/department/get/{id}")
    public DepartmentVO get(@PathVariable Long id);

}

