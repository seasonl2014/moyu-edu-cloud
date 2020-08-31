package cn.xueden.edu.service;


import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.edu.EduTeacherVO;


import java.util.List;
import java.util.Map;

/**功能描述：讲师 服务接口类
 * @Auther:梁志杰
 * @Date:2020/8/30
 * @Description:cn.xueden.edu.service
 * @version:1.0
 */
public interface IEduTeacherService{

    /**
     * 分页获取讲师列表
     * @param pageNum
     * @param pageSize
     * @param eduTeacherVO
     * @return
     */
    PageVO<EduTeacherVO> findEduTeacherList(Integer pageNum, Integer pageSize, EduTeacherVO eduTeacherVO);

    /**
     * 添加讲师
     * @param eduTeacherVO
     */
    void add(EduTeacherVO eduTeacherVO);

    /**
     * 删除讲师
     * @param id
     */
    void delete(Long id);

    /**
     * 编辑讲师
     * @param id
     * @return
     */
    EduTeacherVO edit(Long id);

    /**
     * 更新 讲师
     * @param id
     * @param eduTeacherVO
     */
    void update(Long id,EduTeacherVO eduTeacherVO);


}
