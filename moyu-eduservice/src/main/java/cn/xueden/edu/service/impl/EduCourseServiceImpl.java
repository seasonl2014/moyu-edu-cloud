package cn.xueden.edu.service.impl;

import cn.xueden.common.entity.edu.EduCourse;
import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.edu.EduCourseVO;
import cn.xueden.edu.converter.EduCourseConverter;
import cn.xueden.edu.mapper.EduCourseMapper;
import cn.xueden.edu.service.IEduCourseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**功能描述：课程管理业务接口实现类
 * @Auther:梁志杰
 * @Date:2020/9/1
 * @Description:cn.xueden.edu.service.impl
 * @version:1.0
 */
@Service
@Transactional
public class EduCourseServiceImpl implements IEduCourseService {

    @Autowired
    private EduCourseMapper eduCourseMapper;

    /**
     * 分页获取课程列表
     * @param pageNum
     * @param pageSize
     * @param eduCourseVO
     * @return
     */
    @Override
    public PageVO<EduCourseVO> findCourseList(Integer pageNum, Integer pageSize, EduCourseVO eduCourseVO) {
        PageHelper.startPage(pageNum, pageSize);
        List<EduCourse> eduCourses;
        Example o = new Example(EduCourse.class);
        Example.Criteria criteria = o.createCriteria();
        if (eduCourseVO.getStatus() != null&& eduCourseVO.getStatus().trim().length()>0) {
            criteria.andEqualTo("status", eduCourseVO.getStatus());
        }

        if (eduCourseVO.getTitle() != null && !"".equals(eduCourseVO.getTitle())) {
            criteria.andLike("title", "%" + eduCourseVO.getTitle() + "%");
        }

        eduCourses = eduCourseMapper.selectByExample(o);
        List<EduCourseVO> categoryVOS= EduCourseConverter.converterToVOList(eduCourses);
        PageInfo<EduCourse> info = new PageInfo<>(eduCourses);
        return new PageVO<>(info.getTotal(), categoryVOS);
    }

    /**
     * 添加商品
     * @param eduCourseVO
     */
    @Override
    public void add(EduCourseVO eduCourseVO) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(eduCourseVO,eduCourse);
        eduCourse.setGmtCreate(new Date());
        eduCourse.setGmtModified(new Date());
        @NotNull(message = "分类不能为空") Long[] categoryKeys = eduCourseVO.getCategoryKeys();

        if(categoryKeys.length>0){
            eduCourse.setSubjectParentId(categoryKeys[0]);
            eduCourse.setSubjectId(categoryKeys[1]);
        }
        eduCourse.setBuyCount(0l);//购买人数
        eduCourse.setVipCount(0l);//vip购买人数
        eduCourse.setViewCount(0l);//查看人数
        eduCourse.setVersion(0l);//乐观锁
        eduCourse.setStatus("Draft");//未发布
        eduCourseMapper.insert(eduCourse);
    }

    /**
     * 编辑课程
     * @param id
     * @return
     */
    @Override
    public EduCourseVO edit(Long id) {
        EduCourse eduCourse = eduCourseMapper.selectByPrimaryKey(id);
        return EduCourseConverter.converterToCourseVO(eduCourse);
    }
}
