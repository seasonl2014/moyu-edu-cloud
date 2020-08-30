package cn.xueden.edu.service.impl;

import cn.xueden.common.entity.Department;
import cn.xueden.common.entity.edu.EduTeacher;
import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.edu.EduTeacherVO;
import cn.xueden.edu.mapper.EduTeacherMapper;
import cn.xueden.edu.service.IEduTeacherService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**讲师 服务实现类
 * @Auther:梁志杰
 * @Date:2020/8/30
 * @Description:cn.xueden.edu.service.impl
 * @version:1.0
 */
@Service
public class EduTeacherServiceImpl  implements IEduTeacherService {

   /* @Autowired
    private EduCourseService eduCourseService;*/

   @Autowired
   private EduTeacherMapper eduTeacherMapper;

    /**
     * 分页获取讲师列表
     * @param pageNum
     * @param pageSize
     * @param eduTeacherVO
     * @return
     */
    @Override
    public PageVO<EduTeacherVO> findEduTeacherList(Integer pageNum, Integer pageSize, EduTeacherVO eduTeacherVO) {
        Example o = new Example(Department.class);
        if(eduTeacherVO.getName()!=null&&!"".equals(eduTeacherVO.getName())){
            o.createCriteria().andLike("name","%"+eduTeacherVO.getName()+"%");
        }

        //分页获取部门列表
        PageHelper.startPage(pageNum,pageSize);
        List<EduTeacher> eduTeachers =eduTeacherMapper.selectByExample(o);
        //转VO
        List<EduTeacherVO> eduTeacherVOS = new ArrayList<>();
        if(!CollectionUtils.isEmpty(eduTeachers)){
            for(EduTeacher eduTeacher:eduTeachers){
                EduTeacherVO d = new EduTeacherVO();
                BeanUtils.copyProperties(eduTeacher,d);
                eduTeacherVOS.add(d);

            }
        }

        PageInfo<EduTeacher> info = new PageInfo<>(eduTeachers);
        return new PageVO<>(info.getTotal(),eduTeacherVOS);
    }



}
