package cn.xueden.edu.converter;

import cn.xueden.common.entity.edu.EduCourse;
import cn.xueden.common.vo.edu.EduCourseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**功能描述：课程转换类
 * @Auther:梁志杰
 * @Date:2020/9/1
 * @Description:cn.xueden.edu.converter
 * @version:1.0
 */
public class EduCourseConverter {

    /**
     * 转VOList
     * @param eduCourses
     * @return
     */
    public static List<EduCourseVO> converterToVOList(List<EduCourse> eduCourses) {
        List<EduCourseVO> eduCourseVOS=new ArrayList<>();
        if(!CollectionUtils.isEmpty(eduCourses)){
            for (EduCourse eduCourse : eduCourses) {
                EduCourseVO eduCourseVO = converterToCourseVO(eduCourse);
                eduCourseVOS.add(eduCourseVO);
            }
        }
        return eduCourseVOS;
    }

    /**
     * 转VO
     * @param eduCourse
     * @return
     */
    public static EduCourseVO converterToCourseVO(EduCourse eduCourse) {
        EduCourseVO eduCourseVO = new EduCourseVO();
        BeanUtils.copyProperties(eduCourse,eduCourseVO);
        return eduCourseVO;
    }
}
