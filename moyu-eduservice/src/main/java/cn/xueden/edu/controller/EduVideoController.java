package cn.xueden.edu.controller;

import cn.xueden.common.bean.ResponseBean;
import cn.xueden.common.entity.edu.EduVideo;
import cn.xueden.common.vo.edu.EduChapterVO;
import cn.xueden.common.vo.edu.EduVideoVO;
import cn.xueden.edu.service.IEduVideoService;
import cn.xueden.logging.annotation.LogControllerEndpoint;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**功能描述：课程小节控制层
 * @Auther:梁志杰
 * @Date:2020/9/2
 * @Description:cn.xueden.edu.controller
 * @version:1.0
 */
@Api(tags = "课程大纲小节接口")
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    
    @Autowired
    private IEduVideoService eduVideoService;

    /**
     * 添加课程小节
     * @return
     */
    @LogControllerEndpoint(exceptionMessage = "添加课程小节失败", operation = "课程小节资料添加")
    @ApiOperation(value = "添加课程小节")
    @RequiresPermissions({"video:add"})
    @PostMapping("/add")
    public ResponseBean add(@RequestBody @Validated EduVideoVO eduVideoVO) {
        eduVideoService.add(eduVideoVO);
        return ResponseBean.success();
    }

    /**
     * 根据id查询
     * @param videoId
     * @return
     */
    @GetMapping("{videoId}")
    public EduVideo getVideoById(@PathVariable Long videoId){
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return eduVideo;
    }
}
