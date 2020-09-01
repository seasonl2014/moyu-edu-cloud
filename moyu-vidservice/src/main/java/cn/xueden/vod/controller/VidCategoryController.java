package cn.xueden.vod.controller;

import cn.xueden.common.bean.ResponseBean;
import cn.xueden.common.entity.edu.dto.CategoryDto;
import cn.xueden.common.vo.edu.EduSubjectVO;
import cn.xueden.vod.service.IVidCategoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**功能描述：阿里云点播控制层
 * @Auther:梁志杰
 * @Date:2020/8/31
 * @Description:cn.xueden.vod.controller
 * @version:1.0
 */
@Api("阿里云点播接口")
@RestController
@RequestMapping("/vidservice/vodcategory")
public class VidCategoryController {

    @Autowired
    private IVidCategoryService vidCategoryService;

    /**
     * 添加阿里云点播分类
     * @param eduSubjectVO
     *        分类名称
     * @return
     */
    @PostMapping("addVodCategory")
    public CategoryDto addVodCategory(@RequestBody EduSubjectVO eduSubjectVO){
        CategoryDto categoryDto = vidCategoryService.addCategory(eduSubjectVO);
        if (categoryDto!=null) {
            return categoryDto;
        }else {
            return null;
        }
    }

    /**
     * 删除阿里云点播分类
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public boolean deleteSubjectById(@PathVariable Long id){
        boolean flag = vidCategoryService.deleteSubjectById(id);
        return flag;
    }

    /**
     * 删除阿里云点播分类
     * @param eduSubjectVO
     * @return
     */
    @PutMapping("updateSubjectById")
    public boolean updateSubjectById(@RequestBody EduSubjectVO eduSubjectVO){
        boolean flag = vidCategoryService.updateSubjectById(eduSubjectVO);
        return flag;
    }

}
