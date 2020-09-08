package cn.xueden.edu.service;


import cn.xueden.common.entity.edu.EduVideo;
import cn.xueden.common.vo.edu.EduVideoVO;




/**功能描述：课程大纲小节业务接口
 * @Auther:梁志杰
 * @Date:2020/9/1
 * @Description:cn.xueden.edu.service
 * @version:1.0
 */
public interface IEduVideoService {


    /**
     * 添加课程小节
     * @param eduVideoVO
     */
    void add(EduVideoVO eduVideoVO);

    /**
     * 编辑课程课时
     * @param id
     * @return
     */
    EduVideoVO edit(Long id);

    /**
     * 更新课时
     * @param id
     * @param eduVideoVO
     */
    void update(Long id, EduVideoVO eduVideoVO);

    /**
     * 删除课时
     * @param id
     */
    void delete(Long id);

    /**
     * 根据ID获取视频小节信息
     * @param id
     * @return
     */
    EduVideo getById(Long id);

    /**
     * 根据文件标志获取视频信息
     * @param fileKey
     * @return
     */
    EduVideo getVideoByfileKey(String fileKey);

    /**
     * 根据ID视频信息
     * @return
     */
    EduVideo updateById(EduVideo eduVideo);


}
