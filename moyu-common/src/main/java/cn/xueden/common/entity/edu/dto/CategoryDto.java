package cn.xueden.common.entity.edu.dto;

import lombok.Data;

/**视频分类dto
 * @Auther:梁志杰
 * @Date:2020/8/31
 * @Description:cn.xueden.common.entity.edu.dto
 * @version:1.0
 */
@Data
public class CategoryDto {

    /**
     * 父级Id
     */
    private Long parentId;

    /**
     * 分类Id
     */
    private Long cateId;

    /**
     * 分类名称
     */
    private String cateName;

    /**
     * 分类级别
     */
    private Long level;
}
