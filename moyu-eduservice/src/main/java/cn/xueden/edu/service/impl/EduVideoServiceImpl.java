package cn.xueden.edu.service.impl;

import cn.xueden.common.entity.edu.EduChapter;
import cn.xueden.common.entity.edu.EduVideo;
import cn.xueden.common.vo.edu.EduVideoVO;
import cn.xueden.edu.mapper.EduChapterMapper;
import cn.xueden.edu.mapper.EduVideoMapper;
import cn.xueden.edu.service.IEduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**功能描述：课程小节业务接口实现类
 * @Auther:梁志杰
 * @Date:2020/9/2
 * @Description:cn.xueden.edu.service.impl
 * @version:1.0
 */
@Service
@Transactional
public class EduVideoServiceImpl implements IEduVideoService {

    @Autowired
    private EduVideoMapper eduVideoMapper;

    @Autowired
    private EduChapterMapper eduChapterMapper;

    /**
     * 添加课程大章
     * @param eduVideoVO
     */
    @Override
    public void add(EduVideoVO eduVideoVO) {

        // 获取大章信息
        EduChapter eduChapter = eduChapterMapper.selectByPrimaryKey(eduVideoVO.getChapterId());

        EduVideo eduVideo = new EduVideo();
        BeanUtils.copyProperties(eduVideoVO,eduVideo);
        eduVideo.setGmtCreate(new Date());
        eduVideo.setGmtModified(new Date());
        eduVideo.setDuration(0f);
        eduVideo.setPlayCount(0l);
        // 获取课程ID
        if(eduChapter!=null){
            eduVideo.setCourseId(eduChapter.getCourseId());
        }
        eduVideo.setVersion(1l);// 乐观锁
        eduVideoMapper.insert(eduVideo);
    }


    /**
     * 根据id获取视频小节信息
     * @param id
     * @return
     */
    @Override
    public EduVideo getById(Long id) {
        return eduVideoMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据文件标志获取视频小节信息
     * @param fileKey
     * @return
     */
    @Override
    public EduVideo getVideoByfileKey(String fileKey) {
        EduVideo eduVideo = new EduVideo();
        eduVideo.setFileKey(fileKey);
        return eduVideoMapper.selectOne(eduVideo);
    }



    /**
     * 根据ID更新视频信息
     * @param eduVideo
     * @return
     */
    @Override
    public EduVideo updateById(EduVideo eduVideo) {
        eduVideoMapper.updateByPrimaryKeySelective(eduVideo);
        eduVideo.setGmtCreate(null);
        eduVideo.setGmtModified(null);
        return eduVideo;
    }
}
