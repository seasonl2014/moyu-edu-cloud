package cn.xueden.edu.converter;

import cn.xueden.common.entity.edu.EduVipType;
import cn.xueden.common.vo.edu.EduVipTypeVO;
import org.springframework.beans.BeanUtils;

/**功能描述：会员类型转换类
 * @Auther:梁志杰
 * @Date:2020/9/8
 * @Description:cn.xueden.edu.converter
 * @version:1.0
 */
public class EduVipTypeConverter {

    /**
     * 转VO
     * @param eduVipType
     * @return
     */
    public static EduVipTypeVO converterToEduVipTypeVO(EduVipType eduVipType){
        EduVipTypeVO eduVipTypeVO = new EduVipTypeVO();
        BeanUtils.copyProperties(eduVipType,eduVipTypeVO);
        return eduVipTypeVO;
    }
}
