package cn.xueden.ucenter.controller;

import cn.xueden.common.bean.ResponseBean;
import cn.xueden.common.vo.MenuNodeVO;
import cn.xueden.common.vo.UserInfoVO;
import cn.xueden.logging.service.ILoginLogService;
import cn.xueden.ucenter.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**功能描述：用户模块控制层
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/17
 * @Description:cn.xueden.ucenter.controller
 * @version:1.0
 */
@RestController
@RequestMapping("ucenter/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ILoginLogService loginLogService;

    @ApiOperation(value = "用户登入", notes = "接收参数用户名和密码,登入成功后,返回JWTToken")
    @PostMapping("/login")
    public ResponseBean login(@NotBlank(message = "账号必填") String username,
                              @NotBlank(message = "密码必填")String password,
                              HttpServletRequest request){

       String token = userService.login(username,password);
        loginLogService.add(request);
        return ResponseBean.success((Object)token);

    }

    @ApiOperation(value = "用户信息",notes = "用户登入信息")
    @RequestMapping("/info")
    public ResponseBean info(){
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO = userService.info();
        return ResponseBean.success(userInfoVO);
    }

    /**
     * 加载用户菜单
     * @return
     */
    @ApiOperation(value = "加载用户菜单",notes = "用户登录后，根据角色加载菜单树")
    @GetMapping("/findMenu")
    public ResponseBean findMenu(){
        List<MenuNodeVO> menuNodeVOS = userService.findMenu();
        return ResponseBean.success(menuNodeVOS);
    }

}
