package cn.xueden.vod.controller;

import cn.xueden.common.bean.ResponseBean;
import cn.xueden.common.entity.edu.EduVideo;
import cn.xueden.vod.service.IVidService;
import cn.xueden.vod.utils.AliyunVODSDKUtils;
import cn.xueden.vod.utils.ConstantPropertiesUtil;
import cn.xueden.vod.utils.RedisUtils;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**功能描述：视频模块控制层
 * @Auther:梁志杰
 * @Date:2020/9/3
 * @Description:cn.xueden.vod.controller
 * @version:1.0
 */
@Api("阿里云点播接口")
@RestController
@RequestMapping("/vidservice/vod")
@CrossOrigin
public class VidController {

    @Autowired
    private IVidService vidService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 重置上传进度
     * @param request
     */
    @RequestMapping ("/percent/reset")
    public void resetPercent(HttpServletRequest request){
        String videoId = request.getParameter("videoId");
        redisUtils.del("upload_percent:"+videoId);
    }


    /**
     * 获取实时长传进度
     * @param request
     * @return
     */
    @GetMapping("getUploadPercent")
    public ResponseBean getUploadPercent(HttpServletRequest request){
        String videoId = request.getParameter("videoId");
        System.out.println("从redis获取=========方法====："+redisUtils.get("upload_percent:"+videoId));
        redisUtils.get("upload_percent:"+videoId);
        int percent = redisUtils.get("upload_percent:"+videoId) == null ? 0: (int) redisUtils.get("upload_percent:"+videoId);
        //int percent = session.getAttribute("upload_percent") == null ? 0: (int) session.getAttribute("upload_percent");
        return ResponseBean.success(percent);
    }


    /**
     * 根据章节id实现上传视频到阿里云服务器的方法
     * @return
     */
    @PostMapping("uploadById")
    public ResponseBean uploadAliyunVideoById(@RequestParam("file") MultipartFile file, @RequestParam("id")Long id,
                                              @RequestParam("fileKey")String fileKey,HttpServletRequest request){
        HttpSession session = request.getSession();
        //调用方法实现视频上传，返回上传之后的视频id
        try {
            // 判断视频是否已经上传过了
            if(fileKey!=null){
                EduVideo eduVideo = vidService.getVideoByfileKey(fileKey);
                if(eduVideo!=null){
                    return ResponseBean.error(201,"极速秒传完成");
                }else{
                    String videoId = vidService.uploadAliyunVideoById(file,id,redisUtils,fileKey);
                    return ResponseBean.success(videoId);
                }
            }else {
                String videoId = vidService.uploadAliyunVideoById(file,id,redisUtils,fileKey);
                return ResponseBean.success(videoId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBean.error("上传失败");
        }
    }

    /**
     * 根据视频id获取播放凭证
     * @param vid
     * @return
     */
    @GetMapping("getPlayAuth/{vid}")
    public ResponseBean getPlayAutoId(@PathVariable String vid){
        try {
            //初始化客户端、请求对象和相应对象
            DefaultAcsClient client = AliyunVODSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

            //设置请求参数
            request.setVideoId(vid);
            //获取请求响应
            response = client.getAcsResponse(request);

            //输出请求结果
            //播放凭证
            String playAuth = response.getPlayAuth();
            return ResponseBean.success(playAuth);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBean.error("获取播放凭证失败");
        }

    }


}
