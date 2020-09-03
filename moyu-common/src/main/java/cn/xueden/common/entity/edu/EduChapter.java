package cn.xueden.common.entity.edu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**功能描述：课程大章实体类
 * @Auther:梁志杰
 * @Date:2020/9/2
 * @Description:cn.xueden.common.entity.edu
 * @version:1.0
 */
@Data
@Table(name = "edu_chapter")
public class EduChapter {

    @ApiModelProperty(value = "章节ID")
    @Id
    private Long id;

    @ApiModelProperty(value = "课程ID")
    private Long courseId;

    @ApiModelProperty(value = "章节名称")
    private String title;

    @ApiModelProperty(value = "章节时长")
    private Long duration;

    @ApiModelProperty(value = "显示排序")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;

}
