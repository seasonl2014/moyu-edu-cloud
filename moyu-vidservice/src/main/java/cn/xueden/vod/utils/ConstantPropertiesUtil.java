package cn.xueden.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Auther:梁志杰
 * @Date:2020/8/31
 * @Description:cn.xueden.vod.utils
 * @version:1.0
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${aliyun.vod.file.keyid}")
    private String keyid;

    @Value("${aliyun.vod.file.keysecret}")
    private String keysecret;

    @Value("${aliyun.vod.file.templategroupid}")
    private String templategroupid;//转码组ID

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;

    public static String TEMPLATE_GROUPID;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyid;
        ACCESS_KEY_SECRET = keysecret;
        TEMPLATE_GROUPID = templategroupid;
    }
}
