package cn.xueden.system.controller;

import cn.xueden.common.bean.ResponseBean;
import cn.xueden.common.entity.Role;
import cn.xueden.common.vo.MenuNodeVO;
import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.RoleVO;
import cn.xueden.logging.annotation.ControllerEndpoint;
import cn.xueden.system.service.IMenuService;
import cn.xueden.system.service.IRoleService;
import com.wuwenze.poi.ExcelKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**功能描述：角色管理模块控制层
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/20
 * @Description:cn.xueden.system.controller
 * @version:1.0
 */
@Api(tags = "系统角色接口")
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IMenuService menuService;

    @ApiOperation(value = "角色列表")
    @GetMapping("/findRoleList")
    public ResponseBean findRoleList(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                     @RequestParam(value = "pageSize",defaultValue = "15")Integer pageSize,
                                     RoleVO roleVO){
        PageVO<RoleVO> roleList = roleService.findRoleList(pageNum,pageSize,roleVO);
        return ResponseBean.success(roleList);
    }

    /**
     * 更新角色状态
     * @param id
     * @param status
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "角色状态更新失败",operation = "角色|禁用/启用")
    @ApiOperation(value = "更新角色状态",notes = "禁用和启用两种状态")
    @RequiresPermissions({"role:status"})
    @PutMapping("/updateStatus/{id}/{status}")
    public ResponseBean updateStatus(@PathVariable Long id,
                                     @PathVariable Boolean status){
        roleService.updateStatus(id,status);
        return ResponseBean.success();
    }

    /**
     * 添加角色
     * @param roleVO
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "添加角色失败",operation = "添加角色")
    @ApiOperation(value = "添加角色")
    @RequiresPermissions({"role:add"})
    @PostMapping("/add")
    public ResponseBean add(@RequestBody @Validated RoleVO roleVO){
        roleService.add(roleVO);
        return ResponseBean.success();
    }

    /**
     * 编辑角色
     * @param id
     * @return
     */
    @ApiOperation(value = "编辑就是",notes = "根据ID获取角色信息")
    @RequiresPermissions({"role:edit"})
    @GetMapping("/edit/{id}")
    public ResponseBean edit(@PathVariable Long id){
        RoleVO roleVO = roleService.edit(id);
        return ResponseBean.success(roleVO);
    }

    /**
     * 更新角色信息
     * @param id
     * @param roleVO
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "更新角色失败",operation = "更新角色")
    @ApiOperation(value = "更新角色",notes = "根据ID更新角色信息")
    @RequiresPermissions({"role:update"})
    @PutMapping("/update/{id}")
    public ResponseBean update(@PathVariable Long id,@RequestBody @Validated RoleVO roleVO){
        roleService.update(id,roleVO);
        return ResponseBean .success();
    }

    /**
     * 角色拥有的菜单权限ID和菜单树
     * @param id
     * @return
     */
    @ApiOperation(value = "角色菜单")
    @GetMapping("/findRoleMenu/{id}")
    public ResponseBean findRoleMenu(@PathVariable Long id){
        //获取所有菜单 构建菜单树
        List<MenuNodeVO> tree = menuService.findMenuTree();

        //角色拥有的菜单ID
        List<Long> mids = roleService.findMenuIdsByRoleId(id);

        List<Long> ids = menuService.findOpenIds();

        Map<String,Object> map =new HashMap<>();
        map.put("tree",tree);
        map.put("mids",mids);
        map.put("open",ids);
        return ResponseBean.success(map);
    }

    /**
     * 角色授权
     * @param id
     * @param mids
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "角色授权失败",operation = "角色授权")
    @ApiOperation(value = "角色授权")
    @RequiresPermissions({"role:authority"})
    @PostMapping("/authority/{id}")
    public ResponseBean authority(@PathVariable Long id,@RequestBody Long[] mids){
        roleService.authority(id,mids);
        return ResponseBean.success();

    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "删除角色失败",operation = "删除角色")
    @ApiOperation(value = "删除角色",notes = "根据角色ID删除角色信息")
    @RequiresPermissions({"role:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseBean delete(@PathVariable Long id){
        roleService.deleteById(id);
        return ResponseBean.success();
    }

    /**
     * 导出excel
     * @param response
     */
    @ControllerEndpoint(exceptionMessage = "导出excel失败",operation = "导出角色excel")
    @ApiOperation(value = "导出excel",notes = "导出所有角色的excel表格")
    @PostMapping("/excel")
    @RequiresPermissions({"role:export"})
    public void export(HttpServletResponse response){
        List<Role> roles = roleService.findAll();
        ExcelKit.$Export(Role.class,response).downXlsx(roles,false);
    }

}
