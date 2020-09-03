package cn.xueden.common.entity.edu.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**功能描述：章节dto对象
 * dto思想：需要什么，构建什么
 * @Auther:梁志杰
 * @Date:2020/9/2
 * @Description:cn.xueden.common.entity.edu.dto
 * @version:1.0
 */
@Data
public class EduChapterDto {

    private String id;
    private String title;

    List<EduVideoDto> children = new ArrayList<>();
}
