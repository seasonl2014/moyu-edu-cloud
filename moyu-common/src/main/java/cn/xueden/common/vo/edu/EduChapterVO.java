package cn.xueden.common.vo.edu;


import cn.xueden.common.entity.edu.EduVideo;
import lombok.Data;


import java.util.Date;
import java.util.List;

/**功能描述：章节视图对象
 * @Auther:梁志杰
 * @Date:2020/9/2
 * @Description:cn.xueden.common.vo.edu
 * @version:1.0
 */
@Data
public class EduChapterVO {

    private Long id;

    private Long courseId;

    private String title;

    private Long duration;

    private Integer sort;

    private Date gmtCreate;

    private Date gmtModified;

    private List<EduVideo> children;
}

