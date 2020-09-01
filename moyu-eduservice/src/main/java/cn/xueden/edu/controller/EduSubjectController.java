package cn.xueden.edu.controller;

import cn.xueden.common.bean.ResponseBean;
import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.edu.EduSubjectTreeNodeVO;
import cn.xueden.common.vo.edu.EduSubjectVO;
import cn.xueden.edu.service.IEduSubjectService;
import cn.xueden.logging.annotation.LogControllerEndpoint;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**功能描述： 课程分类控制层
 * @Auther:梁志杰
 * @Date:2020/8/31
 * @Description:cn.xueden.edu.controller
 * @version:1.0
 */
@Api(tags = "课程类别接口")
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private IEduSubjectService eduSubjectService;

    /**
     * 分类树形结构(分页)
     *
     * @return
     */
    @ApiOperation(value = "分类树形结构")
    @GetMapping("/categoryTree")
    public ResponseBean categoryTree(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        PageVO<EduSubjectTreeNodeVO> pageVO = eduSubjectService.categoryTree(pageNum, pageSize);
        return ResponseBean.success(pageVO);
    }

    /**
     * 获取父级分类树：2级树
     *
     * @return
     */
    @ApiOperation(value = "父级分类树")
    @GetMapping("/getParentEduSubjectTreeNode")
    public ResponseBean getParentEduSubjectTreeNode() {
        List<EduSubjectTreeNodeVO> parentTree = eduSubjectService.getParentEduSubjectTree();
        return ResponseBean.success(parentTree);
    }


    /**
     * 添加课程分类
     *
     * @return
     */
    @LogControllerEndpoint(exceptionMessage = "课程分类添加失败", operation = "课程分类添加")
    @RequiresPermissions({"subject:add"})
    @ApiOperation(value = "添加分类")
    @PostMapping("/add")
    public ResponseBean add(@RequestBody @Validated EduSubjectVO eduSubjectVO) {
        eduSubjectService.add(eduSubjectVO);
        return ResponseBean.success();
    }

    /**
     * 删除课程分类
     *
     * @param id
     * @return
     */
    @LogControllerEndpoint(exceptionMessage = "课程分类删除失败", operation = "课程分类删除")
    @ApiOperation(value = "删除分类")
    @RequiresPermissions({"subject:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseBean delete(@PathVariable Long id) {
        eduSubjectService.delete(id);
        return ResponseBean.success();
    }

    /**
     * 编辑课程分类
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "编辑分类")
    @RequiresPermissions({"subject:edit"})
    @GetMapping("/edit/{id}")
    public ResponseBean edit(@PathVariable Long id) {
        EduSubjectVO eduSubjectVO = eduSubjectService.edit(id);
        return ResponseBean.success(eduSubjectVO);
    }

    /**
     * 更新课程分类
     *
     * @return
     */
    @LogControllerEndpoint(exceptionMessage = "课程分类更新失败", operation = "课程分类更新")
    @ApiOperation(value = "更新分类")
    @RequiresPermissions({"subject:update"})
    @PutMapping("/update/{id}")
    public ResponseBean update(@PathVariable Long id, @RequestBody @Validated EduSubjectVO eduSubjectVO) {
        eduSubjectService.update(id, eduSubjectVO);
        return ResponseBean.success();
    }
    
    
}
