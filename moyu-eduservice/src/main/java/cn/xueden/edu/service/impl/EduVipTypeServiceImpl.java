package cn.xueden.edu.service.impl;

import cn.xueden.common.entity.edu.EduSubject;
import cn.xueden.common.entity.edu.EduVipType;
import cn.xueden.common.entity.edu.EduVipTypeSubject;
import cn.xueden.common.exception.ServiceException;
import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.edu.EduVipTypeVO;
import cn.xueden.edu.converter.EduVipTypeConverter;
import cn.xueden.edu.mapper.EduSubjectMapper;
import cn.xueden.edu.mapper.EduVipTypeMapper;
import cn.xueden.edu.mapper.EduVipTypeSubjectMapper;
import cn.xueden.edu.service.IEduVipTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**功能描述：会员类型业务接口实现类
 * @Auther:梁志杰
 * @Date:2020/9/8
 * @Description:cn.xueden.edu.service.impl
 * @version:1.0
 */
@Service
public class EduVipTypeServiceImpl implements IEduVipTypeService {

    @Autowired
    private EduVipTypeMapper vipTypeMapper;

    @Autowired
    private EduVipTypeSubjectMapper vipTypeSubjectMapper;

    @Autowired
    private EduSubjectMapper subjectMapper;

    /**
     * 分页获取会员类型
     * @param pageNum
     * @param pageSize
     * @param eduVipTypeVO
     * @return
     */
    @Override
    public PageVO<EduVipTypeVO> findVipTypeList(Integer pageNum, Integer pageSize, EduVipTypeVO eduVipTypeVO) {
        Example o = new Example(EduVipType.class);
        if(eduVipTypeVO.getName()!=null&&!"".equals(eduVipTypeVO.getName())){
            o.createCriteria().andLike("name","%"+eduVipTypeVO.getName()+"%");
        }

        //分页获取会员类型列表
        PageHelper.startPage(pageNum,pageSize);
        List<EduVipType> eduVipTypes =vipTypeMapper.selectByExample(o);
        //转VO
        List<EduVipTypeVO> eduVipTypeVOS = new ArrayList<>();
        if(!CollectionUtils.isEmpty(eduVipTypes)){
            for(EduVipType eduVipType:eduVipTypes){
                EduVipTypeVO d = new EduVipTypeVO();
                BeanUtils.copyProperties(eduVipType,d);



              /*  User user = userMapper.selectByPrimaryKey(d.getMgrId());
                d.setMgrName(user.getUsername());

                //统计这个会员类型下有多少人
                Example o1 = new Example(User.class);
                o1.createCriteria().andEqualTo("departmentId",department.getId())
                        .andNotEqualTo("type", UserTypeEnum.SYSTEM_ADMIN.getTypeCode());
                d.setTotal(userMapper.selectCountByExample(o1));*/
                d.setMemberTotal(0);
                eduVipTypeVOS.add(d);

            }
        }
        PageInfo<EduVipType> info = new PageInfo<>(eduVipTypes);
        return new PageVO<>(info.getTotal(),eduVipTypeVOS);
    }


    /**
     * 添加会员类型
     * @param eduVipTypeVO
     */
    @Override
    public void add(EduVipTypeVO eduVipTypeVO) {
        EduVipType eduVipType = new EduVipType();
        BeanUtils.copyProperties(eduVipTypeVO,eduVipType);
        eduVipType.setGmtCreate(new Date());
        eduVipType.setGmtModified(new Date());
        vipTypeMapper.insert(eduVipType);
    }

    /**
     * 编辑会员类型
     * @param id
     * @return
     */
    @Override
    public EduVipTypeVO edit(Long id) {
        EduVipType eduVipType = vipTypeMapper.selectByPrimaryKey(id);
        if(eduVipType==null){
            throw  new ServiceException("编辑会员类型不存在");
        }
        return EduVipTypeConverter.converterToEduVipTypeVO(eduVipType);
    }

    /**
     * 更新会员类型
     * @param id
     * @param eduVipTypeVO
     */
    @Override
    public void update(Long id, EduVipTypeVO eduVipTypeVO) {

        EduVipType dbEduVipType = vipTypeMapper.selectByPrimaryKey(id);
        if(dbEduVipType==null){
            throw new ServiceException("需要更新的会员类型不存在");
        }
        EduVipType eduVipType = new EduVipType();
        BeanUtils.copyProperties(eduVipTypeVO,eduVipType);
        eduVipType.setId(id);
        eduVipType.setGmtModified(new Date());
        vipTypeMapper.updateByPrimaryKeySelective(eduVipType);
    }

    /**
     * 删除会员类型
     * @param id
     */
    @Override
    public void delete(Long id) {
        EduVipType dbEduVipType= vipTypeMapper.selectByPrimaryKey(id);
        if(dbEduVipType==null){
            throw new ServiceException("要删除的会员类型不存在");
        }
        vipTypeMapper.deleteByPrimaryKey(id);
        //删除会员类和课程栏目关系记录
        Example o = new Example(EduVipTypeSubject.class);
        o.createCriteria().andEqualTo("vipId",dbEduVipType.getId());
        vipTypeSubjectMapper.deleteByExample(o);
    }

    /**
     * 分配角色
     * @param id
     * @param rids
     */
    @Override
    public void assignVipType(Long id, Long[] rids) {
        //先清空该会员类型原有的权益
        EduVipType dbEduVipType = vipTypeMapper.selectByPrimaryKey(id);
        if(dbEduVipType==null){
            throw new ServiceException("会员类型不存在");
        }
        //删除之前分配的权益
        Example o = new Example(EduVipTypeSubject.class);
        o.createCriteria().andEqualTo("vipId",dbEduVipType.getId());
        vipTypeSubjectMapper.deleteByExample(o);
        //增加现在分配的权益
        if(rids.length>0){
            for(Long rid:rids){
                EduSubject eduSubject = subjectMapper.selectByPrimaryKey(rid);
                if(eduSubject==null){
                    throw new ServiceException("课程类别Id="+rid+",该课程类别不存在");
                }

                EduVipTypeSubject eduVipTypeSubject = new EduVipTypeSubject();
                eduVipTypeSubject.setVipId(id);
                eduVipTypeSubject.setSubjectId(rid);
                eduVipTypeSubject.setGmtCreate(new Date());
                eduVipTypeSubject.setGmtModified(new Date());
                vipTypeSubjectMapper.insert(eduVipTypeSubject);

            }
        }

    }

    /**
     * 获取会员类型已有的课程类别ID
     * @param id
     * @return
     */
    @Override
    public List<Long> subjects(Long id) {
        // 获取会员类别
        EduVipType dbEduVipType = vipTypeMapper.selectByPrimaryKey(id);
        if(dbEduVipType==null){
            throw  new ServiceException("该会员类别不存在");
        }
        Example o = new Example(EduVipTypeSubject.class);
        o.createCriteria().andEqualTo("vipId",dbEduVipType.getId());
        List<EduVipTypeSubject> eduVipTypeSubjectList = vipTypeSubjectMapper.selectByExample(o);
        // 课程类别ID
        List<Long> subjectIds = new ArrayList<>();
        if(!CollectionUtils.isEmpty(eduVipTypeSubjectList)){
            for(EduVipTypeSubject vipTypeSubject:eduVipTypeSubjectList){
                subjectIds.add(vipTypeSubject.getSubjectId());
            }
        }
        return subjectIds;
    }

}
