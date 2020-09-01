package cn.xueden.common.vo.edu;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**功能描述：课程分类视图对象
 * @Auther:梁志杰
 * @Date:2020/8/31
 * @Description:cn.xueden.common.vo.edu
 * @version:1.0
 */
@Data
public class EduSubjectVO {

    private Long id;

    @NotBlank(message = "类目名称不能为空")
    private String name;

    /** 对应阿里云点播类目ID*/
    private Long cateId;

    @NotNull(message = "排序号不能为空")
    private Integer sort;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date gmtCreate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date gmtModified;

    /** 父级分类id*/
    private Long parentId;
}
