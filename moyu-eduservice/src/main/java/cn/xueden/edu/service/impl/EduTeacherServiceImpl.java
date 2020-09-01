package cn.xueden.edu.service.impl;


import cn.xueden.common.entity.edu.EduTeacher;
import cn.xueden.common.exception.ServiceException;
import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.edu.EduTeacherVO;
import cn.xueden.edu.converter.TeacherConverter;
import cn.xueden.edu.mapper.EduTeacherMapper;
import cn.xueden.edu.service.IEduTeacherService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotNull;
import java.util.*;

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
        Example o = new Example(EduTeacher.class);
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


    /**
     * 添加讲师
     * @param eduTeacherVO
     */
    @Override
    public void add(EduTeacherVO eduTeacherVO) {
        EduTeacher eduTeacher = new EduTeacher();
        BeanUtils.copyProperties(eduTeacherVO,eduTeacher);
        eduTeacher.setIsDeleted(0);
        eduTeacher.setGmtCreate(new Date());
        eduTeacher.setGmtModified(new Date());
        eduTeacherMapper.insert(eduTeacher);
    }

    /**
     * 删除讲师
     * @param id
     */
    @Override
    public void delete(Long id) {
        EduTeacher eduTeacher = eduTeacherMapper.selectByPrimaryKey(id);
        if(eduTeacher==null){
            throw new ServiceException("要删除的部门不存在");
        }
        eduTeacherMapper.deleteByPrimaryKey(id);
    }

    /**
     * 编辑 讲师
     * @param id
     * @return
     */
    @Override
    public EduTeacherVO edit(Long id) {
        EduTeacher eduTeacher = eduTeacherMapper.selectByPrimaryKey(id);
        if(eduTeacher==null){
            throw  new ServiceException("编辑讲师不存在");
        }
        return TeacherConverter.converterToEduTeacherVO(eduTeacher);
    }


    /**
     * 更新 讲师
     * @param id
     * @param eduTeacherVO
     */
    @Override
    public void update(Long id, EduTeacherVO eduTeacherVO) {

        EduTeacher dbEduTeacher = eduTeacherMapper.selectByPrimaryKey(id);
        if(dbEduTeacher==null){
            throw new ServiceException("需要更新的讲师不存在");
        }
        EduTeacher teacher = new EduTeacher();
        BeanUtils.copyProperties(eduTeacherVO,teacher);
        teacher.setId(id);
        teacher.setGmtModified(new Date());
        eduTeacherMapper.updateByPrimaryKeySelective(teacher);

    }

    /**
     * 获取全部讲师信息
     * @return
     */
    @Override
    public List<EduTeacher> findAll() {
        return eduTeacherMapper.selectAll();
    }
}
