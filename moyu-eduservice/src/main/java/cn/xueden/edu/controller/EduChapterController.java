package cn.xueden.edu.controller;

import cn.xueden.common.bean.ResponseBean;
import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.edu.EduChapterTreeNodeVO;
import cn.xueden.common.vo.edu.EduChapterVO;
import cn.xueden.common.vo.edu.EduCourseVO;
import cn.xueden.edu.service.IEduChapterService;
import cn.xueden.edu.service.IEduCourseService;
import cn.xueden.logging.annotation.LogControllerEndpoint;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**功能描述：课程大章管理模块控制层
 * @Auther:梁志杰
 * @Date:2020/9/1
 * @Description:cn.xueden.edu.controller
 * @version:1.0
 */
@Api(tags = "课程大章大章接口")
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private IEduChapterService chapterService;

    /**
     * 章节树形结构(分页)
     *
     * @return
     */
    @ApiOperation(value = "章节树形结构")
    @GetMapping("/chapterTree")
    public ResponseBean chapterTree(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                     @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                    EduChapterVO eduChapterVO) {
        if(eduChapterVO==null){
            return ResponseBean.error("获取章节失败");
        }else{
            if(eduChapterVO.getCourseId()==null){
                return ResponseBean.error("获取章节失败");
            }
        }
        PageVO<EduChapterTreeNodeVO> pageVO = chapterService.chapterTree(pageNum, pageSize,eduChapterVO);
        return ResponseBean.success(pageVO);
    }
    

    /**
     * 添加课程大章
     * @return
     */
    @LogControllerEndpoint(exceptionMessage = "添加课程大章失败", operation = "课程大章资料添加")
    @ApiOperation(value = "添加课程大章")
    @RequiresPermissions({"chapter:add"})
    @PostMapping("/add")
    public ResponseBean add(@RequestBody @Validated EduChapterVO eduChapterVO) {
        chapterService.add(eduChapterVO);
        return ResponseBean.success();
    }

    /**
     * 编辑课程大章
     * @param id
     * @return
     */
    @ApiOperation(value = "编辑课程大章", notes = "编辑课程大章信息")
    @RequiresPermissions({"chapter:edit"})
    @GetMapping("/edit/{id}")
    public ResponseBean edit(@PathVariable Long id) {
        EduChapterVO eduChapterVO = chapterService.edit(id);
        return ResponseBean.success(eduChapterVO);
    }

    /**
     * 更新课程大章
     *
     * @return
     */
    @LogControllerEndpoint(exceptionMessage = "更新课程大章失败", operation = "课程大章资料更新")
    @ApiOperation(value = "更新课程大章", notes = "更新课程大章信息")
    @RequiresPermissions({"chapter:update"})
    @PutMapping("/update/{id}")
    public ResponseBean update(@PathVariable Long id, @RequestBody EduChapterVO eduChapterVO) {
        chapterService.update(id, eduChapterVO);
        return ResponseBean.success();
    }

    /**
     * 删除课程大章
     * @param id
     * @return
     */
    @LogControllerEndpoint(exceptionMessage = "删除课程大章失败", operation = "课程大章删除")
    @ApiOperation(value = "删除课程大章", notes = "删除课程大章信息")
    @RequiresPermissions({"chapter:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseBean delete(@PathVariable Long id) {
        chapterService.delete(id);
        return ResponseBean.success();
    }


}
