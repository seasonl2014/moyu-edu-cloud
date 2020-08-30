package cn.xueden.common.config.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/15
 * @Description:cn.xueden.common.config.jwt
 * @version:1.0
 */
public class JWTToken implements AuthenticationToken {

    //密钥
    private String token;

    public JWTToken(String token){
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
