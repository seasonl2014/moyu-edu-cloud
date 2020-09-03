package cn.xueden.common.client;

import cn.xueden.common.entity.edu.EduCourse;
import cn.xueden.common.entity.edu.EduVideo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**功能描述：调用教育微服务
 * @Auther:梁志杰
 * @Date:2020/9/3
 * @Description:cn.xueden.common.client
 * @version:1.0
 */
@FeignClient("moyu-eduservice") //找到注册中心里的online-eduservice服务
@Component
public interface EduClient {

    //根据id更新视频小节信息
    @PostMapping("/eduservice/video/updateVideoById")
    public EduVideo updateVideoById(@RequestParam("videoId") String videoId, @RequestParam("id") Long id,
                                    @RequestParam("duration")Float duration,
                                    @RequestParam("size")Long size);


    //根据视频id获取小节视频信息
    @GetMapping("/eduservice/video/{videoId}")
    public EduVideo getVideoById(@PathVariable("videoId") Long videoId);

    //根据课程id获取课程信息
    @GetMapping("/eduservice/getAllCourseInfo/{courseId}")
    public EduCourse getAllCourseInfo(@PathVariable("courseId") Long courseId);
}
