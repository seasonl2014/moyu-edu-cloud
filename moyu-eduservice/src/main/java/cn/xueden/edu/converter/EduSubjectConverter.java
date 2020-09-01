package cn.xueden.edu.converter;

import cn.xueden.common.entity.edu.EduSubject;
import cn.xueden.common.vo.edu.EduSubjectTreeNodeVO;
import cn.xueden.common.vo.edu.EduSubjectVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**功能描述：课程分类转换类
 * @Auther:梁志杰
 * @Date:2020/8/31
 * @Description:cn.xueden.edu.converter
 * @version:1.0
 */
public class EduSubjectConverter {

    /**
     * 转vo
     * @param eduSubject
     * @return
     */
    public static EduSubjectVO converterToEduSubjectVO(EduSubject eduSubject) {
        EduSubjectVO eduSubjectVO = new EduSubjectVO();
        BeanUtils.copyProperties(eduSubject,eduSubjectVO);
        return eduSubjectVO;
    }

    /**
     * 转voList
     * @param eduSubjects
     * @return
     */
    public static List<EduSubjectVO> converterToVOList(List<EduSubject> eduSubjects) {
        List<EduSubjectVO> EduSubjectVOS=new ArrayList<>();
        if(!CollectionUtils.isEmpty(eduSubjects)){
            for (EduSubject eduSubject : eduSubjects) {
                EduSubjectVO eduSubjectVO = new EduSubjectVO();
                BeanUtils.copyProperties(eduSubject,eduSubjectVO);
                EduSubjectVOS.add(eduSubjectVO);
            }
        }
        return EduSubjectVOS;
    }


    /**
     * 转树节点
     * @param eduSubjectVOList
     * @return
     */
    public static List<EduSubjectTreeNodeVO> converterToTreeNodeVO(List<EduSubjectVO> eduSubjectVOList) {
        List<EduSubjectTreeNodeVO> nodes=new ArrayList<>();
        if(!CollectionUtils.isEmpty(eduSubjectVOList)){
            for (EduSubjectVO eduSubjectVO : eduSubjectVOList) {
                EduSubjectTreeNodeVO eduSubjectTreeNodeVO = new EduSubjectTreeNodeVO();
                BeanUtils.copyProperties(eduSubjectVO,eduSubjectTreeNodeVO);
                nodes.add(eduSubjectTreeNodeVO);
            }
        }
        return nodes;
    }
}
