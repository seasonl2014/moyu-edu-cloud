package cn.xueden.vod.service;

import cn.xueden.common.entity.edu.dto.CategoryDto;
import cn.xueden.common.vo.edu.EduSubjectVO;

/**功能描述：阿里云视频点播分类业务接口
 * @Auther:梁志杰
 * @Date:2020/8/31
 * @Description:cn.xueden.vod.service
 * @version:1.0
 */
public interface IVidCategoryService {

    /**
     * 添加分类
     * @param eduSubjectVO
     * @return
     */
    CategoryDto addCategory(EduSubjectVO eduSubjectVO);

    /**
     * 删除分类
     * @param id
     * @return
     */
    boolean deleteSubjectById(Long id);

    /**
     * 修改分类
     * @param id
     * @return
     */
     boolean  updateSubjectById(EduSubjectVO eduSubjectVO);

}
