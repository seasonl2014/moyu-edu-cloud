package cn.xueden.edu.converter;

import cn.xueden.common.entity.edu.EduTeacher;
import cn.xueden.common.vo.edu.EduTeacherVO;
import org.springframework.beans.BeanUtils;

/**功能描述：讲师转换类
 * @Auther:梁志杰
 * @Date:2020/8/31
 * @Description:cn.xueden.edu.converter
 * @version:1.0
 */
public class TeacherConverter {

    /**
     * 转VO
     * @param teacher
     * @return
     */
    public static EduTeacherVO converterToEduTeacherVO(EduTeacher teacher){
        EduTeacherVO eduTeacherVO = new EduTeacherVO();
        BeanUtils.copyProperties(teacher,eduTeacherVO);
        return eduTeacherVO;
    }
}
