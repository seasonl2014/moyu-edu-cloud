package cn.xueden.edu.converter;

import cn.xueden.common.entity.edu.EduVideo;
import cn.xueden.common.vo.edu.EduVideoVO;
import org.springframework.beans.BeanUtils;

/**功能描述：课时转换类
 * @Auther:梁志杰
 * @Date:2020/9/6
 * @Description:cn.xueden.edu.converter
 * @version:1.0
 */
public class EduVideoConverter {

    /**
     * 转vo
     * @param eduVideo
     * @return
     */
    public static EduVideoVO converterToEduChapterVO(EduVideo eduVideo) {
        EduVideoVO eduVideoVO = new EduVideoVO();
        BeanUtils.copyProperties(eduVideo,eduVideoVO);
        return eduVideoVO;
    }
}
