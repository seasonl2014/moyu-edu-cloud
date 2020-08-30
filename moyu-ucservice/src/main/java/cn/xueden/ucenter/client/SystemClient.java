package cn.xueden.ucenter.client;

import cn.xueden.common.vo.DepartmentVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**功能描述：调用系统微服务
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/23
 * @Description:cn.xueden.ucenter.client
 * @version:1.0
 */
@FeignClient("moyu-sysservice") //找到注册中心里的moyu-sysservice微服务
@Component
public interface SystemClient {

    /**
     * 根据Id获取部门信息
     * @param id
     * @return
     */
    @GetMapping("/system/department/get/{id}")
    public DepartmentVO get(@PathVariable Long id);

}
