package cn.xueden.vod.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

/**功能描述：视频业务接口
 * @Auther:梁志杰
 * @Date:2020/9/3
 * @Description:cn.xueden.vod.service
 * @version:1.0
 */
public interface IVidService {

    /**
     * 根据章节id实现上传视频到阿里云服务器的方法
     * @param file
     * @return
     */
    String uploadAliyunVideoById(MultipartFile file, Long id, HttpSession session);

}
