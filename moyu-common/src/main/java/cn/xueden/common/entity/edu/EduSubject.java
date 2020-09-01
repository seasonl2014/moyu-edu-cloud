package cn.xueden.common.entity.edu;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**功能描述：课程分类实体类
 * @Auther:梁志杰
 * @Date:2020/8/31
 * @Description:cn.xueden.common.entity.edu
 * @version:1.0
 */
@Data
@Table(name = "edu_subject")
public class EduSubject {

    @Id
    private Long id;

    private String name;

    private Long cateId;// 对应阿里云视频点播分类

    private Integer sort;

    private Date gmtCreate;

    private Date gmtModified;

    private Long parentId;


}
