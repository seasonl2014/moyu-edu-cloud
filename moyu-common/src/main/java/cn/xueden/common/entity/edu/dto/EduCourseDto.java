package cn.xueden.common.entity.edu.dto;

import lombok.Data;

/**功能描述：课程数据传输对象
 * @Auther:梁志杰
 * @Date:2020/9/3
 * @Description:cn.xueden.common.entity.edu.dto
 * @version:1.0
 */
@Data
public class EduCourseDto {

    // 课程ID
    private Long id;

    // 课程类别ID
    private Long subjectId;
}
