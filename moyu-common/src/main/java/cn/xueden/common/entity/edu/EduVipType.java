package cn.xueden.common.entity.edu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**功能描述：会员类型实体类
 * @Auther:梁志杰
 * @Date:2020/9/8
 * @Description:cn.xueden.common.entity.edu
 * @version:1.0
 */
@Data
@Table(name = "edu_vip_type")
public class EduVipType {

    @ApiModelProperty(value = "会员类型ID")
    @Id
    private Long id;

    @ApiModelProperty(value = "类型名称")
    private String name;

    @ApiModelProperty(value = "会员价格")
    private Double vipMoney;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;
}
