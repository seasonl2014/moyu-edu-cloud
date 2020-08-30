package cn.xueden.common.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

/**功能描述：加密工具类
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/17
 * @Description:cn.xueden.common.utils
 * @version:1.0
 */
public class MD5Utils {
    /**
     * 密码加密
     * @return
     */
    public static String md5Encryption(String source,String salt){
        String algorithmName = "MD5";//加密算法
        int hashIterations = 1024;//加密次数
        SimpleHash simpleHash = new SimpleHash(algorithmName,source,salt,hashIterations);
        return simpleHash+"";
    }

}
