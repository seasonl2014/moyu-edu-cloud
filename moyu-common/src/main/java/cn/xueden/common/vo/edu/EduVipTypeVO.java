package cn.xueden.common.vo.edu;

import cn.xueden.common.entity.edu.EduSubject;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**功能描述：会员类型视图对象类
 * @Auther:梁志杰
 * @Date:2020/9/8
 * @Description:cn.xueden.common.vo.edu
 * @version:1.0
 */
@Data
public class EduVipTypeVO {

    private Long id;

    private String name;

    private Double vipMoney;

    private int memberTotal;

    private Date gmtCreate;

    private Date gmtModified;
}
