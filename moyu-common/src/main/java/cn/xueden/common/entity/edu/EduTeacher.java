package cn.xueden.common.entity.edu;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @Auther:梁志杰
 * @Date:2020/8/30
 * @Description:cn.xueden.common.entity.edu
 * @version:1.0
 */
@Excel("teacher")
@Data
@Table(name = "edu_teacher")
public class EduTeacher {

    @Id
    @ApiModelProperty(value = "讲师ID")
    @ExcelField(value = "编号", width = 50)
    private Long id;

    @ExcelField(value = "讲师名称", width = 100)
    @ApiModelProperty(value = "讲师姓名")
    private String name;

    @ExcelField(value = "讲师资历", width = 120)
    @ApiModelProperty(value = "讲师资历,一句话说明讲师")
    private String intro;

    @ExcelField(value = "讲师简介", width = 120)
    @ApiModelProperty(value = "讲师简介")
    private String career;

    @ExcelField(value = "头衔", width = 100)
    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "讲师头像")
    private String avatar;

    @ExcelField(value = "排序", width = 100)
    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    private Integer isDeleted;

    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;
}
