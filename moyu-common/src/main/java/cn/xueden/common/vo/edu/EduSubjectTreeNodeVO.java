package cn.xueden.common.vo.edu;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**功能描述：课程分类树形视图对象
 * @Auther:梁志杰
 * @Date:2020/8/31
 * @Description:cn.xueden.common.vo.edu
 * @version:1.0
 */
@Data
public class EduSubjectTreeNodeVO {

    private Long id;

    private String name;

    private Long cateId;

    private Integer sort;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;

    private Long parentId;

    private int lev;

    private List<EduSubjectTreeNodeVO> children;

    /*
     * 排序,根据order排序
     */
    public static Comparator<EduSubjectTreeNodeVO> order(){
        Comparator<EduSubjectTreeNodeVO> comparator = (o1, o2) -> {
            if(o1.getSort() != o2.getSort()){
                return (int) (o1.getSort() - o2.getSort());
            }
            return 0;
        };
        return comparator;
    }
}
