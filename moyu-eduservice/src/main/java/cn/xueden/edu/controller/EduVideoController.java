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
     * 编辑课程课时
     * @param id
     * @return
     */
    @ApiOperation(value = "编辑课程课时", notes = "编辑课程课时信息")
    @RequiresPermissions({"video:edit"})
    @GetMapping("/edit/{id}")
    public ResponseBean edit(@PathVariable Long id) {
        EduVideoVO eduVideoVO = eduVideoService.edit(id);
        return ResponseBean.success(eduVideoVO);
    }

    /**
     * 更新课程大章
     *
     * @return
     */
    @LogControllerEndpoint(exceptionMessage = "更新课时失败", operation = "课时更新")
    @ApiOperation(value = "更新课时", notes = "更新课时信息")
    @RequiresPermissions({"video:update"})
    @PutMapping("/update/{id}")
    public ResponseBean update(@PathVariable Long id, @RequestBody EduVideoVO eduVideoVO) {
        eduVideoService.update(id, eduVideoVO);
        return ResponseBean.success();
    }

    /**
     * 删除课时
     * @param id
     * @return
     */
    @LogControllerEndpoint(exceptionMessage = "删除课时失败", operation = "课时删除")
    @ApiOperation(value = "删除课时", notes = "删除课时信息")
    @RequiresPermissions({"video:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseBean delete(@PathVariable Long id) {
        eduVideoService.delete(id);
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
        eduVideo.setGmtCreate(null);
        eduVideo.setGmtModified(null);
        return eduVideo;
    }

    /**
     * 根据文件标志获取视频信息
     * @param fileKey
     * @return
     */
    @GetMapping("getVideoByfileKey/{fileKey}")
    public EduVideo getVideoByfileKey(@PathVariable String fileKey){
        EduVideo eduVideo = eduVideoService.getVideoByfileKey(fileKey);
        if(eduVideo!=null){
            eduVideo.setGmtCreate(null);
            eduVideo.setGmtModified(null);
        }
        return eduVideo;
    }

    /**
     * 根据章节ID修改视频信息（主要更新阿里云视频源Id）
     * @param videoId
     *        视频源Id
     * @param id
     *        章节id
     * @param duration
     *        时长
     * @param size
     *        视频大小
     * @param fileKey
     *        文件标志
     * @return
     */
    @PutMapping("updateVideoById")
    public EduVideo updateVideoById(@RequestParam("videoId")String videoId,
                             @RequestParam("id")Long id,
                             @RequestParam("duration")Float duration,
                             @RequestParam("size")Long size,
                             @RequestParam("fileKey")String fileKey){
        EduVideo eduVideo = new EduVideo();
        eduVideo.setId(id);
        eduVideo.setVideoSourceId(videoId);
        eduVideo.setDuration(duration);
        eduVideo.setSize(size);
        eduVideo.setFileKey(fileKey);
        return eduVideoService.updateById(eduVideo);
    }
}
