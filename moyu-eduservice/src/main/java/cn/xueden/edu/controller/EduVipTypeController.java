package cn.xueden.edu.controller;

import cn.xueden.common.bean.ResponseBean;
import cn.xueden.common.entity.edu.EduSubject;
import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.edu.EduSubjectVO;
import cn.xueden.common.vo.edu.EduVipTypeVO;
import cn.xueden.common.vo.edu.SubjectTransferItemVO;
import cn.xueden.edu.converter.EduSubjectConverter;
import cn.xueden.edu.service.IEduSubjectService;
import cn.xueden.edu.service.IEduVipTypeService;
import cn.xueden.logging.annotation.LogControllerEndpoint;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**功能描述：会员类型控制层
 * @Auther:梁志杰
 * @Date:2020/9/8
 * @Description:cn.xueden.edu.controller
 * @version:1.0
 */
@Api(tags = "会员类型接口")
@RestController
@RequestMapping("/eduservice/type")
public class EduVipTypeController {

    @Autowired
    private IEduVipTypeService vipTypeService;

    @Autowired
    private IEduSubjectService subjectService;

    /**
     * 分页获取会员类型列表
     * @param pageNum
     * @param pageSize
     * @param eduVipTypeVO
     * @return
     */
    @ApiOperation(value = "会员类型列表",notes = "会员类型列表，根据会员类型名称模糊查询")
    @GetMapping("/findVipTypeList")
    public ResponseBean findVipTypeList(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                           @RequestParam(value = "pageSize",defaultValue = "15")Integer pageSize,
                                        EduVipTypeVO eduVipTypeVO){
        PageVO<EduVipTypeVO> departmentsList = vipTypeService.findVipTypeList(pageNum,pageSize,eduVipTypeVO);
        return ResponseBean.success(departmentsList);
    }

    /**
     * 添加会员类型
     * @param eduVipTypeVO
     * @return
     */
    @RequiresPermissions({"vipType:add"})
    @LogControllerEndpoint(exceptionMessage = "添加会员类型失败",operation = "添加会员类型")
    @ApiOperation(value = "添加会员类型")
    @PostMapping("/add")
    public ResponseBean add(@RequestBody @Validated EduVipTypeVO eduVipTypeVO){
        vipTypeService.add(eduVipTypeVO);
        return ResponseBean.success();
    }

    /**
     * 编辑会员类型
     * @param id
     * @return
     */
    @ApiOperation(value = "编辑会员类型")
    @RequiresPermissions({"vipType:edit"})
    @GetMapping("/edit/{id}")
    public ResponseBean edit(@PathVariable Long id){
        EduVipTypeVO eduVipTypeVO = vipTypeService.edit(id);
        return ResponseBean.success(eduVipTypeVO);
    }

    /**
     * 更新会员类型
     * @param id
     * @param eduVipTypeVO
     * @return
     */
    @LogControllerEndpoint(exceptionMessage = "更新会员类型失败",operation = "更新会员类型")
    @ApiOperation(value = "更新会员类型")
    @RequiresPermissions({"vipType:update"})
    @PutMapping("/update/{id}")
    public ResponseBean update(@PathVariable Long id,
                               @RequestBody @Validated EduVipTypeVO eduVipTypeVO){
        vipTypeService.update(id,eduVipTypeVO);
        return ResponseBean.success();

    }

    /**
     * 删除会员类型
     * @param id
     * @return
     */
    @LogControllerEndpoint(exceptionMessage = "删除会员类型失败",operation = "删除会员类型")
    @ApiOperation(value = "删除会员类型")
    @RequiresPermissions({"vipType:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseBean delete(@PathVariable Long id){
        vipTypeService.delete(id);
        return ResponseBean.success();
    }

    /**
     * 获取会员类型已有权益
     * @param id
     * @return
     */
    @ApiOperation(value = "会员类型已有权益",notes = "根据会员类型ID，获取会员类型已有权益")
    @GetMapping("/{id}/subjects")
    public ResponseBean roles(@PathVariable Long id){
        //根据会员类型Id获取会员类型已有的权益Id
        List<Long> values = vipTypeService.subjects(id);
        List<EduSubjectVO> list = subjectService.findAll();

        //转成前端需要的角色Item
        List<SubjectTransferItemVO> items = EduSubjectConverter.converterToSubjectTransferItem(list);

        Map<String,Object> map = new HashMap<>();
        map.put("subjects", items);
        map.put("values", values);

        return ResponseBean.success(map);

    }

    /**
     * 分配权益
     * @param id
     * @param rids
     * @return
     */
    @LogControllerEndpoint(exceptionMessage = "分配会员类型权益失败",operation = "分配会员类型权益")
    @ApiOperation(value = "分配会员类型权益",notes = "把权益分配给会员类型，一个或者多个")
    @RequiresPermissions({"vipType:assign"})
    @PostMapping("/{id}/assignVipType")
    public ResponseBean assignVipType(@PathVariable Long id,@RequestBody Long[] rids){
        vipTypeService.assignVipType(id,rids);
        return ResponseBean.success();
    }
    
}
