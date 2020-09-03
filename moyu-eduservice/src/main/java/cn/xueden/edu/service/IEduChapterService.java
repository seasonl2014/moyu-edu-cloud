package cn.xueden.edu.service;


import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.edu.EduChapterTreeNodeVO;
import cn.xueden.common.vo.edu.EduChapterVO;

import java.util.List;


/**功能描述：课程大章大章业务接口
 * @Auther:梁志杰
 * @Date:2020/9/1
 * @Description:cn.xueden.edu.service
 * @version:1.0
 */
public interface IEduChapterService {

    /**
     * 查询所课程章节
     * @return
     */
    List<EduChapterVO> findAllByEduChapter(EduChapterVO eduChapterVO);


    /**
     * 添加课程大章
     * @param eduChapterVO
     */
    void add(EduChapterVO eduChapterVO);

    /**
     * 编辑课程大章
     * @param id
     * @return
     */
    EduChapterVO edit(Long id);

    /**
     * 章节树形
     * @return
     */
    PageVO<EduChapterTreeNodeVO> chapterTree(Integer pageNum, Integer pageSize,EduChapterVO eduChapterVO);

}
