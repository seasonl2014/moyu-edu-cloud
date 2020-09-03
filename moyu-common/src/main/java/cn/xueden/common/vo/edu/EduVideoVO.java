package cn.xueden.common.vo.edu;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**功能描述：课程小节实体类
 * @Auther:梁志杰
 * @Date:2020/9/2
 * @Description:cn.xueden.common.vo.edu
 * @version:1.0
 */
@Data
public class EduVideoVO {

    private Long id;

    private Long courseId;

    private Long chapterId;

    private String title;

    private Integer sort;

    private Long playCount;

    private int isFree;

    private String videoSourceId;

    private Float duration;

    private String status;

    private Long size;

    private Long version;

    private Date gmtCreate;

    private Date gmtModified;

    private String videoOriginalName;
}
