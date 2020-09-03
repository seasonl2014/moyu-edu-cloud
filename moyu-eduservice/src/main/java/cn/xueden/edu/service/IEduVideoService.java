package cn.xueden.edu.service;


import cn.xueden.common.entity.edu.EduVideo;
import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.edu.EduChapterTreeNodeVO;
import cn.xueden.common.vo.edu.EduChapterVO;
import cn.xueden.common.vo.edu.EduVideoVO;

import java.util.List;


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
     * 根据ID获取视频小节信息
     * @param id
     * @return
     */
    EduVideo getById(Long id);


}
