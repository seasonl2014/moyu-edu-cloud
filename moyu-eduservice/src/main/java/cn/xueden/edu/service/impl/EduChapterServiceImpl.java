package cn.xueden.edu.service.impl;

import cn.xueden.common.entity.edu.EduChapter;

import cn.xueden.common.entity.edu.EduVideo;
import cn.xueden.common.utils.EduChapterTreeBuilder;
import cn.xueden.common.utils.ListPageUtils;
import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.edu.EduChapterTreeNodeVO;
import cn.xueden.common.vo.edu.EduChapterVO;

import cn.xueden.common.vo.edu.EduVideoVO;
import cn.xueden.edu.converter.EduChapterConverter;
import cn.xueden.edu.mapper.EduChapterMapper;

import cn.xueden.edu.mapper.EduVideoMapper;
import cn.xueden.edu.service.IEduChapterService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**功能描述：课程大章业务接口实现类
 * @Auther:梁志杰
 * @Date:2020/9/1
 * @Description:cn.xueden.edu.service.impl
 * @version:1.0
 */
@Service
@Transactional
public class EduChapterServiceImpl implements IEduChapterService {

    @Autowired
    private EduChapterMapper eduChapterMapper;

    @Autowired
    private EduVideoMapper eduVideoMapper;

    /**
     * 根据课程ID获取所有章节
     * @return
     */
    @Override
    public List<EduChapterVO> findAllByEduChapter(EduChapterVO eduChapterVO) {
        EduChapter eduChapter = new EduChapter();
        eduChapter.setCourseId(eduChapterVO.getCourseId());
        List<EduChapter> eduChapters = eduChapterMapper.select(eduChapter);
        return EduChapterConverter.converterToVOList(eduChapters);
    }

    /**
     * 章节树形结构
     * @return
     */
    @Override
    public PageVO<EduChapterTreeNodeVO> chapterTree(Integer pageNum, Integer pageSize,EduChapterVO eduChapterVO) {
        // 获取课程大章
        List<EduChapterVO> eduChapterVOS = findAllByEduChapter(eduChapterVO);
        List<EduChapterTreeNodeVO> nodeVOS=EduChapterConverter.converterToTreeNodeVO(eduChapterVOS,eduVideoMapper);
        List<EduChapterTreeNodeVO> tree = EduChapterTreeBuilder.build(nodeVOS);
        List<EduChapterTreeNodeVO> page;
        if(pageSize!=null&&pageNum!=null){
            page= ListPageUtils.page(tree, pageSize, pageNum);
            return new PageVO<>(tree.size(),page);
        }else {
            return new PageVO<>(tree.size(), tree);
        }
    }



    /**
     * 添加课程大章
     * @param eduChapterVO
     */
    @Override
    public void add(EduChapterVO eduChapterVO) {
        EduChapter eduChapter = new EduChapter();
        BeanUtils.copyProperties(eduChapterVO,eduChapter);
        eduChapter.setGmtCreate(new Date());
        eduChapter.setGmtModified(new Date());
        eduChapter.setDuration(0l);
        eduChapterMapper.insert(eduChapter);
    }

    /**
     * 编辑课程
     * @param id
     * @return
     */
    @Override
    public EduChapterVO edit(Long id) {
        EduChapter eduChapter = eduChapterMapper.selectByPrimaryKey(id);
        //return EduCourseConverter.converterToCourseVO(eduChapter);
        return null;
    }
}
