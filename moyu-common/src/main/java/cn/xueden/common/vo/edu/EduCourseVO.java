package cn.xueden.common.vo.edu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**功能描述：课程视图对象
 * @Auther:梁志杰
 * @Date:2020/9/1
 * @Description:cn.xueden.common.vo.edu
 * @version:1.0
 */
@Data
public class EduCourseVO {

    private Long id;

    private Long teacherId;


    private String subjectId;

    @NotBlank(message = "课程标题不能为空")
    private String title;

    @NotNull(message = "课程价格不能为空")
    private BigDecimal price;

    @NotNull(message = "课程课时不能为空")
    private Integer lessonNum;

    @NotBlank(message = "课程封面不能为空")
    private String cover;


    private Long buyCount;

    private Long vipCount;


    private Long viewCount;


    private Long version;


    private String status;


    private Date gmtCreate;


    private Date gmtModified;


    private Long subjectParentId;

    @NotNull(message = "分类不能为空")
    private Long[] categoryKeys;

    //难度
    private Integer difficulty;
}
