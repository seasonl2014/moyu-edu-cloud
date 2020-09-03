package cn.xueden.edu.controller;

import cn.xueden.common.bean.ResponseBean;
import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.edu.EduCourseVO;
import cn.xueden.edu.service.IEduCourseService;
import cn.xueden.logging.annotation.LogControllerEndpoint;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**功能描述：课程管理模块控制层
 * @Auther:梁志杰
 * @Date:2020/9/1
 * @Description:cn.xueden.edu.controller
 * @version:1.0
 */
@Api(tags = "课程管理接口")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private IEduCourseService courseService;

    /**
     * 全部课程列表
     * @return
     */
    @ApiOperation(value = "课程列表", notes = "课程列表,根据课程名模糊查询")
    @GetMapping("/findCourseList")
    public ResponseBean findCourseList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                       @RequestParam(value = "pageSize") Integer pageSize,
                                       @RequestParam(value = "categorys", required = false) String categorys,
                                       EduCourseVO courseVO) {
        // buildCategorySearch(categorys, courseVO);
        PageVO<EduCourseVO> CourseVOPageVO = courseService.findCourseList(pageNum, pageSize, courseVO);
        return ResponseBean.success(CourseVOPageVO);
    }

    /**
     * 封装课程查询条件
     * @param categorys
     * @param courseVO
     */
    private void buildCategorySearch(@RequestParam(value = "categorys", required = false) String categorys, EduCourseVO courseVO) {
        if (categorys != null && !"".equals(categorys)) {
            String[] split = categorys.split(",");
           /* switch (split.length) {
                case 1:
                    courseVO.setOneCategoryId(Long.parseLong(split[0]));
                    break;
                case 2:
                    courseVO.setOneCategoryId(Long.parseLong(split[0]));
                    courseVO.setTwoCategoryId(Long.parseLong(split[1]));
                    break;
                case 3:
                    courseVO.setOneCategoryId(Long.parseLong(split[0]));
                    courseVO.setTwoCategoryId(Long.parseLong(split[1]));
                    courseVO.setThreeCategoryId(Long.parseLong(split[2]));
                    break;
            }*/
        }
    }

    /**
     * 添加课程
     * @return
     */
    @LogControllerEndpoint(exceptionMessage = "添加课程失败", operation = "课程资料添加")
    @ApiOperation(value = "添加课程")
    @RequiresPermissions({"course:add"})
    @PostMapping("/add")
    public ResponseBean add(@RequestBody @Validated EduCourseVO eduCourseVO) {
        courseService.add(eduCourseVO);
        return ResponseBean.success();
    }

    /**
     * 编辑课程
     * @param id
     * @return
     */
    @ApiOperation(value = "编辑课程", notes = "编辑课程信息")
    @RequiresPermissions({"course:edit"})
    @GetMapping("/edit/{id}")
    public ResponseBean edit(@PathVariable Long id) {
        EduCourseVO eduCourseVO = courseService.edit(id);
        return ResponseBean.success(eduCourseVO);
    }


}
