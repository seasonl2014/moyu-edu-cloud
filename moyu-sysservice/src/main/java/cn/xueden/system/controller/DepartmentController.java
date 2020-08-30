package cn.xueden.system.controller;

import cn.xueden.common.bean.ResponseBean;
import cn.xueden.common.entity.Department;
import cn.xueden.common.vo.DeanVO;
import cn.xueden.common.vo.DepartmentVO;
import cn.xueden.logging.annotation.ControllerEndpoint;
import cn.xueden.system.service.IDepartmentService;
import com.wuwenze.poi.ExcelKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import cn.xueden.common.vo.PageVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**功能描述：部门管理模块控制层
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/18
 * @Description:cn.xueden.system.controller
 * @version:1.0
 */
@Api(tags = "系统部门接口")
@RestController
@RequestMapping("/system/department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    /**
     * 分页获取部门列表
     * @param pageNum
     * @param pageSize
     * @param departmentVO
     * @return
     */
    @ApiOperation(value = "部门列表",notes = "部门列表，根据部门名称模糊查询")
    @GetMapping("/findDepartmentList")
    public ResponseBean findDepartmentList(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                           @RequestParam(value = "pageSize",defaultValue = "15")Integer pageSize,
                                           DepartmentVO departmentVO){
        PageVO<DepartmentVO> departmentsList = departmentService.findDepartmentList(pageNum,pageSize,departmentVO);
       return ResponseBean.success(departmentsList);
    }

    @ApiOperation(value = "部门主任",notes = "查找部门主任，排除掉被禁用的用户")
    @GetMapping("/findDeanList")
    public ResponseBean findDeanList(){
        List<DeanVO> managerList =departmentService.findDeanList();
        return ResponseBean.success(managerList);
    }

    /**
     * 添加部门
     * @param departmentVO
     * @return
     */
    @RequiresPermissions({"department:add"})
    @ControllerEndpoint(exceptionMessage = "添加部门失败",operation = "添加部门")
    @ApiOperation(value = "添加部门")
    @PostMapping("/add")
    public ResponseBean add(@RequestBody @Validated DepartmentVO departmentVO){
        departmentService.add(departmentVO);
        return ResponseBean.success();
    }

    /**
     * 编辑部门
     * @param id
     * @return
     */
    @ApiOperation(value = "编辑部门")
    @RequiresPermissions({"department:edit"})
    @GetMapping("/edit/{id}")
    public ResponseBean edit(@PathVariable Long id){
        DepartmentVO departmentVO = departmentService.edit(id);
        return ResponseBean.success(departmentVO);
    }

    /**
     * 更新部门
     * @param id
     * @param departmentVO
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "更新部门失败",operation = "更新部门")
    @ApiOperation(value = "更新部门")
    @RequiresPermissions({"department:update"})
    @PutMapping("/update/{id}")
    public ResponseBean update(@PathVariable Long id,
                               @RequestBody @Validated DepartmentVO departmentVO){
        departmentService.update(id,departmentVO);
        return ResponseBean.success();

    }

    @ControllerEndpoint(exceptionMessage = "删除部门失败",operation = "删除部门")
    @ApiOperation(value = "删除部门")
    @RequiresPermissions({"department:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseBean delete(@PathVariable Long id){
        departmentService.delete(id);
        return ResponseBean.success();
    }

    /**
     * 导出部门信息
     * @param response
     */
    @ApiOperation(value = "导出excel",notes = "导出所有部门信息")
    @ControllerEndpoint(exceptionMessage = "导出部门Excel文档失败",operation = "导出部门Excel文档")
    @RequiresPermissions({"department:export"})
    @PostMapping("/excel")
    public void export(HttpServletResponse response){
        List<Department> departments = departmentService.findAll();
        ExcelKit.$Export(Department.class,response).downXlsx(departments,false);
    }

    @ApiOperation(value = "获取所有部门")
    @GetMapping("/findAll")
    public ResponseBean findAll(){
        List<DepartmentVO> departmentVOS = departmentService.findAllVO();
        return ResponseBean.success(departmentVOS);
    }

    /**
     * 获取部门详情
     * @param id
     * @return
     */
    @ApiOperation(value = "获取部门详情")
    @GetMapping("/get/{id}")
    public DepartmentVO get(@PathVariable Long id){
        DepartmentVO departmentVO = departmentService.edit(id);
        return departmentVO;
    }

}
