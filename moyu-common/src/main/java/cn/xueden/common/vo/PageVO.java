package cn.xueden.common.vo;

import lombok.Data;


import java.util.ArrayList;
import java.util.List;

/**功能描述：页面视图对象
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/18
 * @Description:cn.xueden.common.vo
 * @version:1.0
 */
@Data
public class PageVO<T> {

    private long total;

    private List<T> rows = new ArrayList<>();

    public PageVO(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }
}
