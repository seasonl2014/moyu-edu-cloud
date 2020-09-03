package cn.xueden.vod.service.impl;

import cn.xueden.common.client.EduClient;
import cn.xueden.common.entity.edu.EduCourse;
import cn.xueden.common.entity.edu.EduVideo;
import cn.xueden.vod.alivod.PutObjectProgressListener;
import cn.xueden.vod.service.IVidService;
import cn.xueden.vod.utils.ConstantPropertiesUtil;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static cn.xueden.vod.utils.AliyunVODSDKUtils.initVodClient;

/**功能描述：视频业务接口实现类
 * @Auther:梁志杰
 * @Date:2020/9/3
 * @Description:cn.xueden.vod.service.impl
 * @version:1.0
 */
@Service
public class VidServiceImpl implements IVidService {

    @Autowired
    private EduClient eduClient;

    /**
     * 根据章节id实现上传视频到阿里云服务器的方法
     * @param file
     * @return
     */
    @Override
    public String uploadAliyunVideoById(MultipartFile file, Long id, HttpSession session) {
        try {

            //获取小节视频信息
            EduVideo eduVideo= eduClient.getVideoById(id);

            if(eduVideo==null){
                return null;
            }
            //根据课程ID获取课程信息
            EduCourse eduCourse = eduClient.getAllCourseInfo(eduVideo.getCourseId());
            if(eduCourse==null){
                return null;
            }


            String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
            String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
            String fileName = file.getOriginalFilename();

            String title = fileName.substring(0,fileName.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();
            int fileSize = inputStream.available();
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
            request.setCateId(eduCourse.getSubjectId());
            request.setPrintProgress(true);
            /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
            request.setProgressListener(new PutObjectProgressListener(session,fileSize,id));
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n"); //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                System.out.print("VideoId=" + response.getVideoId() + "\n");
            } else {
                /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }

            //获取阿里云视频信息

            Float duration = getVideoInfo(response.getVideoId());
            //调用微服务更新视频
            eduClient.updateVideoById(response.getVideoId(),id,duration,(long)fileSize);
            return response.getVideoId();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取阿里云视频信息(暂时先获取视频时长)
     * @param videoId
     * @return
     */
    public Float getVideoInfo(String videoId){
        try {
            TimeUnit.SECONDS.sleep(5);//秒
            DefaultAcsClient client = initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            GetVideoInfoResponse response = new GetVideoInfoResponse();
            GetVideoInfoRequest request = new GetVideoInfoRequest();
            request.setVideoId(videoId);
            response =client.getAcsResponse(request);
            Float duration = response.getVideo().getDuration();
            return duration;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
