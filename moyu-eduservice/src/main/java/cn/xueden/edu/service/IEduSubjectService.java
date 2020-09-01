package cn.xueden.edu.service;

import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.edu.EduSubjectTreeNodeVO;
import cn.xueden.common.vo.edu.EduSubjectVO;

import java.util.List;

/**功能描述：课程分类业务接口
 * @Auther:梁志杰
 * @Date:2020/8/31
 * @Description:cn.xueden.edu.service
 * @version:1.0
 */
public interface IEduSubjectService {

    /**
     * 查询所课程类别
     * @return
     */
    List<EduSubjectVO> findAll();

    /**
     * 分类树形
     * @return
     */
    PageVO<EduSubjectTreeNodeVO> categoryTree(Integer pageNum, Integer pageSize);

    /**
     * 获取父级分类（2级树）
     * @return
     */
    List<EduSubjectTreeNodeVO> getParentEduSubjectTree();

    /**
     * 添加课程类别
     * @param eduSubjectVO
     */
    void add(EduSubjectVO eduSubjectVO);

    /**
     * 删除课程类别
     * @param id
     */
    void delete(Long id);

    /**
     * 编辑课程类别
     * @param id
     * @return
     */
    EduSubjectVO edit(Long id);

    /**
     * 更新课程类别
     * @param id
     * @param eduSubjectVO
     */
    void update(Long id, EduSubjectVO eduSubjectVO);
}
