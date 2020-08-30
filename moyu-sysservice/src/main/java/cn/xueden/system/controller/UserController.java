package cn.xueden.system.controller;

import cn.xueden.common.bean.ResponseBean;
import cn.xueden.common.entity.Role;
import cn.xueden.common.entity.User;
import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.RoleTransferItemVO;
import cn.xueden.common.vo.UserEditVO;
import cn.xueden.common.vo.UserVO;
import cn.xueden.logging.annotation.ControllerEndpoint;
import cn.xueden.system.converter.RoleConverter;
import cn.xueden.system.service.IRoleService;
import cn.xueden.system.service.IUserService;
import com.wuwenze.poi.ExcelKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**功能描述：用户管理模块控制层
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/22
 * @Description:cn.xueden.system.controller
 * @version:1.0
 */
@Api(value = "系统用户模块",tags = "系统用户接口")
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    /**
     * 分页获取用户列表
     * @param pageNum
     * @param pageSize
     * @param userVO
     * @return
     */
    @ApiOperation(value = "用户列表",notes = "模糊查询用户列表")
    @GetMapping("/findUserList")
    public ResponseBean findUserList(@RequestParam(value = "pegeNum",defaultValue = "1")Integer pageNum,
                                     @RequestParam(value = "pageSize",defaultValue = "15")Integer pageSize,
                                     UserVO userVO){
        PageVO<UserVO> userList = userService.findUserList(pageNum,pageSize,userVO);
        return ResponseBean.success(userList);
    }

    /**
     * 添加用户
     * @param userVO
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "添加用户失败",operation = "添加用户")
    @ApiOperation(value = "添加用户",notes = "添加用户信息")
    @RequiresPermissions({"user:add"})
    @PostMapping("/add")
    public ResponseBean add(@RequestBody @Validated UserVO userVO){
        userService.add(userVO);
        return ResponseBean.success();
    }

    /**
     * 编辑用户
     * @param id
     * @return
     */
    @ApiOperation(value = "编辑用户",notes = "根据用户ID获取用户信息，编辑用户信息")
    @RequiresPermissions({"user:edit"})
    @GetMapping("/edit/{id}")
    public ResponseBean edit(@PathVariable  Long id){
        UserEditVO userEditVO = userService.edit(id);
        return ResponseBean.success(userEditVO);
    }

    /**
     * 更新用户
     * @param id
     * @param userEditVO
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "更新用户失败",operation = "更新用户")
    @ApiOperation(value = "更新用户",notes = "根据用户ID，更新用户信息")
    @RequiresPermissions({"user:update"})
    @PutMapping("/update/{id}")
    public ResponseBean update(@PathVariable Long id,@RequestBody @Validated UserEditVO userEditVO){
        userService.update(id,userEditVO);
        return ResponseBean.success();
    }

    /**
     * 删除用户信息
     * @param id
     * @param request
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "删除用户失败",operation = "删除用户")
    @RequiresPermissions({"user:delete"})
    @ApiOperation(value = "删除用户",notes = "根据用户ID删除用户信息")
    @DeleteMapping("/delete/{id}")
    public ResponseBean delete(@PathVariable Long id, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        userService.deleteById(id,token);
        return ResponseBean.success();
    }

    /**
     * 获取用户已有角色
     * @param id
     * @return
     */
    @ApiOperation(value = "用户已有角色",notes = "根据用户ID，获取用户已经拥有的角色")
    @GetMapping("/{id}/roles")
    public ResponseBean roles(@PathVariable Long id){
        //根据用户Id获取用户已有的角色Id
        List<Long> values = userService.roles(id);
        List<Role> list = roleService.findAll();

        //转成前端需要的角色Item
        List<RoleTransferItemVO> items = RoleConverter.converterToRoleTransferItem(list);

        Map<String,Object> map = new HashMap<>();
        map.put("roles", items);
        map.put("values", values);

        return ResponseBean.success(map);

    }

    /**
     * 分配角色
     * @param id
     * @param rids
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "分配角色失败",operation = "分配角色")
    @ApiOperation(value = "分配角色",notes = "把角色分配给用户，一个或者多个")
    @RequiresPermissions({"user:assign"})
    @PostMapping("/{id}/assignRoles")
    public ResponseBean assignRoles(@PathVariable Long id,@RequestBody Long[] rids){
        userService.assignRoles(id,rids);
        return ResponseBean.success();
    }

    /**
     * 更新用户状态
     * @param id
     * @param status
     * @param request
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "更新用户状态失败",operation = "用户|禁用/启用")
    @ApiOperation(value = "用户状态",notes = "禁用和启用这两种状态")
    @RequiresPermissions({"user:status"})
    @PutMapping("/updateStatus/{id}/{status}")
    public ResponseBean updateStatus(@PathVariable Long id,@PathVariable Boolean status,
                                     HttpServletRequest request){
        String token = request.getHeader("Authorization");
        userService.updateStatus(id,status,token);
        return ResponseBean.success();

    }

    /**
     * 导出用户信息
     * @param response
     */
    @ApiOperation(value = "导出excel",notes = "导出所有用户的excel表格")
    @PostMapping("/excel")
    @RequiresPermissions({"user:export"})
    @ControllerEndpoint(exceptionMessage = "导出excel失败",operation = "导出用户excel")
    public void export(HttpServletResponse response){
        List<User> users = userService.findAll();
        ExcelKit.$Export(User.class,response).downXlsx(users,false);
    }
}
