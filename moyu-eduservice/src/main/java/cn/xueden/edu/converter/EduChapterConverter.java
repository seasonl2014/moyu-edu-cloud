package cn.xueden.edu.converter;

import cn.xueden.common.entity.edu.EduChapter;
import cn.xueden.common.entity.edu.EduSubject;
import cn.xueden.common.entity.edu.EduVideo;
import cn.xueden.common.vo.edu.EduChapterTreeNodeVO;
import cn.xueden.common.vo.edu.EduChapterVO;
import cn.xueden.common.vo.edu.EduSubjectTreeNodeVO;
import cn.xueden.common.vo.edu.EduSubjectVO;
import cn.xueden.edu.mapper.EduVideoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**功能描述：章节转换类
 * @Auther:梁志杰
 * @Date:2020/8/31
 * @Description:cn.xueden.edu.converter
 * @version:1.0
 */
public class EduChapterConverter {


    /**
     * 转vo
     * @param eduChapter
     * @return
     */
    public static EduChapterVO converterToEduChapterVO(EduChapter eduChapter) {
        EduChapterVO eduChapterVO = new EduChapterVO();
        BeanUtils.copyProperties(eduChapter,eduChapterVO);
        return eduChapterVO;
    }

    /**
     * 转voList
     * @param eduChapters
     * @return
     */
    public static List<EduChapterVO> converterToVOList(List<EduChapter> eduChapters) {
        List<EduChapterVO> eduChapterVOS=new ArrayList<>();
        if(!CollectionUtils.isEmpty(eduChapters)){
            for (EduChapter eduChapter : eduChapters) {
                EduChapterVO eduChapterVO = new EduChapterVO();
                BeanUtils.copyProperties(eduChapter,eduChapterVO);
                eduChapterVOS.add(eduChapterVO);
            }
        }
        return eduChapterVOS;
    }


    /**
     * 转树节点
     * @param eduChapterVOList
     * @return
     */
    public static List<EduChapterTreeNodeVO> converterToTreeNodeVO(List<EduChapterVO> eduChapterVOList,
                                                                   EduVideoMapper eduVideoMapper) {
        List<EduChapterTreeNodeVO> nodes=new ArrayList<>();
        if(!CollectionUtils.isEmpty(eduChapterVOList)){
            for (EduChapterVO eduChapterVO : eduChapterVOList) {
                // 获取课程章节下的小节
                EduVideo eduVideo = new EduVideo();
                eduVideo.setChapterId(eduChapterVO.getId());
                List<EduVideo> eduVideoList = eduVideoMapper.select(eduVideo);

                // 统计章节总时长
                float totalChapterDuration=0f;
                for (EduVideo video:eduVideoList){
                    totalChapterDuration+=video.getDuration();
                }
                eduChapterVO.setDuration(totalChapterDuration);
                eduChapterVO.setChildren(eduVideoList);
                EduChapterTreeNodeVO eduSubjectTreeNodeVO = new EduChapterTreeNodeVO();
                BeanUtils.copyProperties(eduChapterVO,eduSubjectTreeNodeVO);
                nodes.add(eduSubjectTreeNodeVO);
            }
        }
        return nodes;
    }
}
