package cn.xueden.vod.controller;

import cn.xueden.common.bean.ResponseBean;
import cn.xueden.vod.service.IVidService;
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

    /**
     * 重置上传进度
     * @param request
     */
    @RequestMapping ("/percent/reset")
    public void resetPercent(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("upload_percent",0);
    }


    /**
     * 获取实时长传进度
     * @param request
     * @return
     */
    @GetMapping("getUploadPercent")
    public ResponseBean getUploadPercent(HttpServletRequest request){
        HttpSession session = request.getSession();
        System.out.println("从session获取=============："+session.getAttribute("upload_percent"));
        int percent = session.getAttribute("upload_percent") == null ? 0: (int) session.getAttribute("upload_percent");
        return ResponseBean.success(percent);
    }


    /**
     * 根据章节id实现上传视频到阿里云服务器的方法
     * @return
     */
    @PostMapping("uploadById")
    public ResponseBean uploadAliyunVideoById(@RequestParam("file") MultipartFile file, @RequestParam("id")Long id,
                                   HttpServletRequest request){
        HttpSession session = request.getSession();
        //调用方法实现视频上传，返回上传之后的视频id
        try {
            String videoId = vidService.uploadAliyunVideoById(file,id,session);
            return ResponseBean.success(videoId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBean.error("上传失败");
        }
    }

}
