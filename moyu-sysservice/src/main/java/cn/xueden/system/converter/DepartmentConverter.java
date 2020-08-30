package cn.xueden.system.converter;

import cn.xueden.common.entity.Department;
import cn.xueden.common.vo.DepartmentVO;
import org.springframework.beans.BeanUtils;

/**功能描述：部门转换类
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/18
 * @Description:cn.xueden.system.converter
 * @version:1.0
 */
public class DepartmentConverter {

    /**
     * 转VO
     * @param department
     * @return
     */
    public static DepartmentVO converterToDepartmentVO(Department department){
        DepartmentVO departmentVO = new DepartmentVO();
        BeanUtils.copyProperties(department,departmentVO);
        return departmentVO;
    }
}
