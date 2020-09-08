package cn.xueden.edu.service;

import cn.xueden.common.entity.edu.dto.EduCourseDto;
import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.edu.EduCourseVO;

/**功能描述：课程管理业务接口
 * @Auther:梁志杰
 * @Date:2020/9/1
 * @Description:cn.xueden.edu.service
 * @version:1.0
 */
public interface IEduCourseService {

    /**
     * 商品列表
     * @param pageNum
     * @param pageSize
     * @param eduCourseVO
     * @return
     */
    PageVO<EduCourseVO> findCourseList(Integer pageNum, Integer pageSize, EduCourseVO eduCourseVO);

    /**
     * 添加课程
     * @param eduCourseVO
     */
    void add(EduCourseVO eduCourseVO);

    /**
     * 编辑课程
     * @param id
     * @return
     */
    EduCourseVO edit(Long id);

    /**
     * 根据课程id查询课程详细信息
     * @param courseId
     * @return
     */
    EduCourseDto getAllCourseInfo(Long courseId);

    /**
     * 更新课程
     * @param id
     * @param eduCourseVO
     */
    void update(Long id, EduCourseVO eduCourseVO);

    /**
     * 删除课程
     * @param id
     */
    void delete(Long id);

}
