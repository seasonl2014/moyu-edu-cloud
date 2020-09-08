package cn.xueden.edu.service.impl;

import cn.xueden.common.client.VidClient;
import cn.xueden.common.entity.edu.EduChapter;
import cn.xueden.common.entity.edu.EduVideo;
import cn.xueden.common.exception.ErrorCodeEnum;
import cn.xueden.common.exception.ServiceException;
import cn.xueden.common.vo.edu.EduVideoVO;
import cn.xueden.edu.converter.EduVideoConverter;
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

    @Autowired
    private VidClient vidClient;

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
     * 编辑课程大章
     * @param id
     * @return
     */
    @Override
    public EduVideoVO edit(Long id) {
        EduVideo eduVideo = eduVideoMapper.selectByPrimaryKey(id);
        return EduVideoConverter.converterToEduChapterVO(eduVideo);
    }

    /**
     * 更新课时
     * @param id
     * @param eduVideoVO
     */
    @Override
    public void update(Long id, EduVideoVO eduVideoVO) {
        EduVideo eduVideo = new EduVideo();
        BeanUtils.copyProperties(eduVideoVO,eduVideo);
        eduVideo.setGmtModified(new Date());
        eduVideoMapper.updateByPrimaryKeySelective(eduVideo);
    }

    /**
     * 删除课时
     * @param id
     */
    @Override
    public void delete(Long id) {
       // 获取课时信息
        EduVideo eduVideo = eduVideoMapper.selectByPrimaryKey(id);
        if(eduVideo==null){
            throw new ServiceException(ErrorCodeEnum.VIDEO_DELETE_ERROR);
        }else {

            if(eduVideo.getVideoSourceId()!=null&&eduVideo.getVideoSourceId().trim().length()>0){
                // 先删除阿里云点播视频
                boolean flag= vidClient.deleteVideoById(eduVideo.getVideoSourceId());
                if(flag){
                    eduVideoMapper.deleteByPrimaryKey(id);
                }
            }else {
                eduVideoMapper.deleteByPrimaryKey(id);
            }


        }
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
