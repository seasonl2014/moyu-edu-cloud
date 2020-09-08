package cn.xueden.common.entity.edu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**功能描述：会员类别和栏目关系实体类
 * @Auther:梁志杰
 * @Date:2020/9/8
 * @Description:cn.xueden.common.entity.edu
 * @version:1.0
 */
@Data
@Table(name = "edu_vip_type_subject")
public class EduVipTypeSubject {

    @ApiModelProperty(value = "ID")
    @Id
    private Long id;

    @ApiModelProperty(value = "会员类别ID")
    private Long vipId;

    @ApiModelProperty(value = "课程类别ID")
    private Long subjectId;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;
}
