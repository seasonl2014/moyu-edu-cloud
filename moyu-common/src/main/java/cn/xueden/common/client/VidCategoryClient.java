package cn.xueden.common.client;

import cn.xueden.common.entity.edu.dto.CategoryDto;
import cn.xueden.common.vo.edu.EduSubjectVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther:梁志杰
 * @Date:2020/8/31
 * @Description:cn.xueden.common.client
 * @version:1.0
 */
@FeignClient("moyu-vidservice") //找到注册中心里的online-vidservice服务
@Component
public interface VidCategoryClient {

    // 添加阿里云视频分类
    @PostMapping("/vidservice/vodcategory/addVodCategory")
    public CategoryDto addVodCategory(@RequestBody EduSubjectVO eduSubjectVO);

    //根据id删除阿里云视频分类
    @DeleteMapping("/vidservice/vodcategory/{cateId}")
    public boolean deleteSubjectById(@PathVariable("cateId") Long cateId);

    //根据id修改阿里云视频分类
    @PutMapping("/vidservice/vodcategory/updateSubjectById")
    public boolean updateSubjectById(@RequestBody EduSubjectVO eduSubjectVO);

}
