package cn.xueden.edu.controller;


import cn.xueden.common.bean.ResponseBean;

import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.edu.EduTeacherVO;
import cn.xueden.edu.service.IEduTeacherService;

import cn.xueden.logging.annotation.ControllerEndpoint;
import cn.xueden.logging.annotation.LogControllerEndpoint;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**讲师 前端控制器
 * @Auther:梁志杰
 * @Date:2020/8/30
 * @Description:cn.xueden.edu.controller
 * @version:1.0
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private IEduTeacherService eduTeacherService;

    /**
     * 分页获取讲师列表
     * @param pageNum
     * @param pageSize
     * @param eduTeacherVO
     * @return
     */
    @ApiOperation(value = "讲师列表",notes = "讲师列表，根据讲师名称模糊查询")
    @GetMapping("/findEduTeacherList")
    public ResponseBean findEduTeacherList(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                           @RequestParam(value = "pageSize",defaultValue = "15")Integer pageSize,
                                           EduTeacherVO eduTeacherVO){
        PageVO<EduTeacherVO> departmentsList = eduTeacherService.findEduTeacherList(pageNum,pageSize,eduTeacherVO);
        return ResponseBean.success(departmentsList);
    }

    /**
     * 添加讲师
     * @param eduTeacherVO
     * @return
     */
    @RequiresPermissions({"teacher:add"})
    @LogControllerEndpoint(exceptionMessage = "添加讲师失败",operation = "添加讲师")
    @ApiOperation(value = "添加讲师")
    @PostMapping("/add")
    public ResponseBean add(@RequestBody @Validated EduTeacherVO eduTeacherVO){
        eduTeacherService.add(eduTeacherVO);
        return ResponseBean.success();
    }

    /**
     * 功能描述：删除讲师
     * @param id
     * @return
     */
    @LogControllerEndpoint(exceptionMessage = "删除讲师失败",operation = "删除讲师")
    @ApiOperation(value = "删除讲师")
    @RequiresPermissions({"teacher:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseBean delete(@PathVariable Long id){
        eduTeacherService.delete(id);
        return ResponseBean.success();
    }

    /**
     * 编辑 讲师
     * @param id
     * @return
     */
    @ApiOperation(value = "编辑讲师")
    @RequiresPermissions({"teacher:edit"})
    @GetMapping("/edit/{id}")
    public ResponseBean edit(@PathVariable Long id){
        EduTeacherVO eduTeacherVO = eduTeacherService.edit(id);
        return ResponseBean.success(eduTeacherVO);
    }

    /**
     * 更新 讲师
     * @param id
     * @param eduTeacherVO
     * @return
     */
    @LogControllerEndpoint(exceptionMessage = "更新讲师失败",operation = "更新讲师")
    @ApiOperation(value = "更新讲师")
    @RequiresPermissions({"teacher:update"})
    @PutMapping("/update/{id}")
    public ResponseBean update(@PathVariable Long id,
                               @RequestBody @Validated EduTeacherVO eduTeacherVO){
        eduTeacherService.update(id,eduTeacherVO);
        return ResponseBean.success();

    }



}
