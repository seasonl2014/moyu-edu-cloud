package cn.xueden.edu.controller;

import cn.xueden.common.bean.R;
import cn.xueden.common.bean.ResponseBean;
import cn.xueden.common.entity.edu.EduTeacher11;
import cn.xueden.common.entity.edu.query.QueryTeacher;
import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.edu.EduTeacherVO;
import cn.xueden.edu.service.IEduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**讲师 前端控制器
 * @Auther:梁志杰
 * @Date:2020/8/30
 * @Description:cn.xueden.edu.controller
 * @version:1.0
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private IEduTeacherService eduTeacherService;

    /**
     * 分页获取部门列表
     * @param pageNum
     * @param pageSize
     * @param eduTeacherVO
     * @return
     */
    @ApiOperation(value = "讲师列表",notes = "讲师列表，根据讲师名称模糊查询")
    @GetMapping("/findEduTeacherList")
    public ResponseBean findDepartmentList(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                           @RequestParam(value = "pageSize",defaultValue = "15")Integer pageSize,
                                           EduTeacherVO eduTeacherVO){
        PageVO<EduTeacherVO> departmentsList = eduTeacherService.findEduTeacherList(pageNum,pageSize,eduTeacherVO);
        return ResponseBean.success(departmentsList);
    }

    /**
     * 1、查询所有的讲师功能
     * @return
     */
    @GetMapping
    public R getAllTeacherList(){
        List<EduTeacher11> teachers = eduTeacherService.list(null);
        return R.ok().data("items",teachers);
    }

    /**
     * 2、逻辑删除讲师
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R deleteTeacherById(@PathVariable String id) {
        boolean flag = eduTeacherService.deleteTeacherById(id);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 3、分页查询讲师列表的方法
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("pageList/{page}/{limit}")
    public R getPageTeacherList(@PathVariable Long page,@PathVariable Long limit){

        //创建page对象，传递两个参数   (当前页数，每页记录数)
        Page<EduTeacher11> pageTeacher = new Page<>(page,limit);

        //调用方法分页查询
        eduTeacherService.page(pageTeacher,null);

        //从pageTeacher对象里面获取分页数据
        long total = pageTeacher.getTotal();
        List<EduTeacher11> records = pageTeacher.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",records);

        return R.ok().data(map);
    }

    /**
     * 4、多条件组合查询带分页
     * (@RequestBody,主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的))
     * (使用@RequestBody时，必须使用@PostMapping提交，否则取不到值)
     * @param page
     * @param limit
     * @param queryTeacher
     * @return
     */
    @PostMapping("moreCondtionPageList/{page}/{limit}")
    public R getMoreCondtionPageList(@PathVariable Long page, @PathVariable Long limit, @RequestBody(required = false) QueryTeacher queryTeacher){
        Page<EduTeacher11> pageTeacher = new Page<>(page,limit);
        //调用service的方法实现条件查询带分页
        eduTeacherService.pageListCondition(pageTeacher,queryTeacher);

        //从pageTeacher对象里面获取分页数据
        long total = pageTeacher.getTotal();
        List<EduTeacher11> records = pageTeacher.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",records);

        return R.ok().data(map);
    }

    /**
     * 5、添加讲师
     * @param eduTeacher
     * @return
     */
    @PostMapping("saveTeacher")
    public R saveTeacher(@RequestBody EduTeacher11 eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 6、根据id查询讲师
     * @param id
     * @return
     */
    @GetMapping("getTeacherInfo/{id}")
    public R getTeacherInfo(@PathVariable String id){
        EduTeacher11 eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("eduTeacher",eduTeacher);
    }

    /**
     * 7、修改讲师
     * @param eduTeacher
     * @return
     */
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher11 eduTeacher){
        boolean b = eduTeacherService.updateById(eduTeacher);
        if(b){
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 模拟登录
     * @return
     */
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

}
