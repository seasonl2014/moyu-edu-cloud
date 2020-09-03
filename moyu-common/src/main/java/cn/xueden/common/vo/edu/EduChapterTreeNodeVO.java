package cn.xueden.common.vo.edu;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**功能描述：章节树形结构视图类
 * @Auther:梁志杰
 * @Date:2020/9/2
 * @Description:cn.xueden.common.vo.edu
 * @version:1.0
 */
@Data
public class EduChapterTreeNodeVO {

    private Long id;

    private String title;

    private Long courseId;

    private String videoSourceId;

    private Integer sort;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;

    private int lev;

    private Long duration;

    private List<EduChapterTreeNodeVO> children;

    /*
     * 排序,根据order排序
     */
    public static Comparator<EduChapterTreeNodeVO> order(){
        Comparator<EduChapterTreeNodeVO> comparator = (o1, o2) -> {
            if(o1.getSort() != o2.getSort()){
                return (int) (o1.getSort() - o2.getSort());
            }
            return 0;
        };
        return comparator;
    }
}
