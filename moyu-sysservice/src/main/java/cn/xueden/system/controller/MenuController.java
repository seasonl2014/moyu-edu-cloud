package cn.xueden.system.controller;

import cn.xueden.common.bean.ResponseBean;
import cn.xueden.common.entity.Menu;
import cn.xueden.common.vo.MenuNodeVO;
import cn.xueden.common.vo.MenuVO;
import cn.xueden.logging.annotation.ControllerEndpoint;
import cn.xueden.system.service.IMenuService;
import com.wuwenze.poi.ExcelKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**功能描述：菜单模块控制层
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/19
 * @Description:cn.xueden.system.controller
 * @version:1.0
 */
@Api(tags = "菜单权限接口")
@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    /**
     * 加载菜单树
     * @return
     */
    @ApiOperation(value = "加载菜单树",notes = "获取所有菜单树，以及展开项")
    @GetMapping("/tree")
    public ResponseBean tree(){
        List<MenuNodeVO> menuTree = menuService.findMenuTree();
        List<Long> ids = menuService.findOpenIds();
        Map<String,Object> map = new HashMap<>();
        map.put("tree",menuTree);
        map.put("open",ids);
        return ResponseBean.success(map);
    }

    /**
     * 新增菜单
     * @param menuVO
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "新增菜单/按钮失败", operation = "新增菜单/按钮")
    @ApiOperation(value = "新增菜单")
    @RequiresPermissions({"menu:add"})
    @PostMapping("/add")
    public ResponseBean add(@RequestBody @Validated MenuVO menuVO){
        Menu menu = menuService.add(menuVO);
        Map<String,Object> map = new HashMap<>();
        map.put("id",menu.getId());
        map.put("menuName",menu.getMenuName());
        map.put("children",new ArrayList<>());
        map.put("icon", menu.getIcon());
        return ResponseBean.success(map);
    }

    /**
     * 编辑菜单
     * @param id
     * @return
     */
    @ApiOperation(value = "菜单详情",notes = "根据菜单ID获取菜单详情")
    @RequiresPermissions({"menu:edit"})
    @GetMapping("/edit/{id}")
    public ResponseBean edit(@PathVariable Long id){
        MenuVO menuVO = menuService.edit(id);
        return ResponseBean.success(menuVO);
    }

    /**
     * 更新菜单
     * @param id
     * @param menuVO
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "更新菜单失败",operation = "更新菜单")
    @ApiOperation(value = "更新菜单",notes = "根据ID更新菜单节点")
    @RequiresPermissions({"menu:update"})
    @PutMapping("/update/{id}")
    public ResponseBean update(@PathVariable Long id,@RequestBody @Validated MenuVO menuVO){
        menuService.update(id,menuVO);
        return ResponseBean.success();
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "删除菜单/按钮失败",operation = "删除菜单")
    @ApiOperation(value = "删除菜单",notes = "根据菜单ID删除菜单")
    @RequiresPermissions({"menu:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseBean delete(@PathVariable Long id){
        menuService.delete(id);
        return ResponseBean.success();
    }

    /**
     * 导出菜单信息
     * @param response
     */
    @ApiOperation(value = "导出excel",notes = "导出所有菜单的excel表格")
    @PostMapping("excel")
    @RequiresPermissions({"menu:export"})
    @ControllerEndpoint(exceptionMessage = "导出excel失败",operation = "导出菜单信息")
    public void export(HttpServletResponse response){
        List<Menu> menus = menuService.findAll();
        ExcelKit.$Export(Menu.class,response).downXlsx(menus,false);
    }
}
