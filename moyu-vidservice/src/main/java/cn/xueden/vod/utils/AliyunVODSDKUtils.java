package cn.xueden.vod.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

/**阿里云视频点播sdk初始化操作
 * @Auther:梁志杰
 * @Date:2020/8/31
 * @Description:cn.xueden.vod.utils
 * @version:1.0
 */
public class AliyunVODSDKUtils {

    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shenzhen";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);

        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
