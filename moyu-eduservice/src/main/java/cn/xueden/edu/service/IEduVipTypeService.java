package cn.xueden.edu.service;

import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.edu.EduVipTypeVO;

import java.util.List;

/**功能描述：会员类型业务接口
 * @Auther:梁志杰
 * @Date:2020/9/8
 * @Description:cn.xueden.edu.service
 * @version:1.0
 */
public interface IEduVipTypeService {

    /**
     * 部门列表
     * @param pageNum
     * @param pageSize
     * @param departmentVO
     * @return
     */
    PageVO<EduVipTypeVO> findVipTypeList(Integer pageNum, Integer pageSize, EduVipTypeVO eduVipTypeVO);

    /**
     * 添加会员类型
     * @param eduVipTypeVO
     */
    void add(EduVipTypeVO eduVipTypeVO);

    /**
     * 编辑会员类型
     * @param id
     * @return
     */
    EduVipTypeVO edit(Long id);

    /**
     * 更新会员类型
     * @param id
     * @param eduVipTypeVO
     */
    void update(Long id,EduVipTypeVO eduVipTypeVO);

    /**
     * 删除会员类型
     * @param id
     */
    void delete(Long id);

    /**
     * 分配权益
     * @param id
     * @param rids
     */
    void assignVipType(Long id,Long[] rids);

    /**
     * 获取会员类型已有的课程类别Id
     * @param id
     * @return
     */
    List<Long> subjects(Long id);
}
