package cn.xueden.edu.service.impl;

import cn.xueden.common.client.VidCategoryClient;
import cn.xueden.common.entity.edu.EduSubject;
import cn.xueden.common.entity.edu.dto.CategoryDto;
import cn.xueden.common.utils.EduSubjectTreeBuilder;
import cn.xueden.common.utils.ListPageUtils;
import cn.xueden.common.vo.PageVO;
import cn.xueden.common.vo.edu.EduSubjectTreeNodeVO;
import cn.xueden.common.vo.edu.EduSubjectVO;
import cn.xueden.edu.converter.EduSubjectConverter;
import cn.xueden.edu.mapper.EduSubjectMapper;
import cn.xueden.edu.service.IEduSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**功能描述：课程分类业务实现类
 * @Auther:梁志杰
 * @Date:2020/8/31
 * @Description:cn.xueden.edu.service.impl
 * @version:1.0
 */
@Service
public class EduSubjectServiceImpl implements IEduSubjectService {

    @Autowired
    private EduSubjectMapper eduSubjectMapper;

    @Autowired
    private VidCategoryClient vidCategoryClient;


    /**
     * 所有商品类别
     * @return
     */
    @Override
    public List<EduSubjectVO> findAll() {
        List<EduSubject> productCategories = eduSubjectMapper.selectAll();
        return EduSubjectConverter.converterToVOList(productCategories);
    }

    /**
     * 分类树形结构
     * @return
     */
    @Override
    public PageVO<EduSubjectTreeNodeVO> categoryTree(Integer pageNum, Integer pageSize) {
        List<EduSubjectVO> productCategoryVOList = findAll();
        List<EduSubjectTreeNodeVO> nodeVOS=EduSubjectConverter.converterToTreeNodeVO(productCategoryVOList);
        List<EduSubjectTreeNodeVO> tree = EduSubjectTreeBuilder.build(nodeVOS);
        List<EduSubjectTreeNodeVO> page;
        if(pageSize!=null&&pageNum!=null){
            page= ListPageUtils.page(tree, pageSize, pageNum);
            return new PageVO<>(tree.size(),page);
        }else {
            return new PageVO<>(tree.size(), tree);
        }
    }

    /**
     * 获取父级分类（2级树）
     * @return
     */
    @Override
    public List<EduSubjectTreeNodeVO> getParentEduSubjectTree() {
        List<EduSubjectVO> productCategoryVOList = findAll();
        List<EduSubjectTreeNodeVO> nodeVOS=EduSubjectConverter.converterToTreeNodeVO(productCategoryVOList);
        return  EduSubjectTreeBuilder.buildParent(nodeVOS);
    }

    /**
     * 添加课程类别
     * @param eduSubjectVO
     */
    @Override
    public void add(EduSubjectVO eduSubjectVO) {
        EduSubject eduSubject = new EduSubject();
        BeanUtils.copyProperties(eduSubjectVO,eduSubject);
        eduSubject.setGmtCreate(new Date());
        eduSubject.setGmtModified(new Date());

        // 获取阿里云点播对应课程分类ID

        // 如果父节点不为空
        if(eduSubjectVO.getParentId()!=null){
            EduSubject dbEduSubject = eduSubjectMapper.selectByPrimaryKey(eduSubjectVO.getParentId());
            if(dbEduSubject!=null){
                eduSubjectVO.setCateId(dbEduSubject.getCateId());
            }
        }

        CategoryDto categoryDto = vidCategoryClient.addVodCategory(eduSubjectVO);
        if(categoryDto!=null){
            eduSubject.setCateId(categoryDto.getCateId());
            eduSubjectMapper.insert(eduSubject);
        }


    }


    /**
     * 删除课程类别
     * @param id
     */
    @Override
    public void delete(Long id) {
        EduSubject dbEduSubject = eduSubjectMapper.selectByPrimaryKey(id);
        if(dbEduSubject!=null){
            if(dbEduSubject.getCateId()!=null){
              boolean flag = vidCategoryClient.deleteSubjectById(dbEduSubject.getCateId());
              if(flag){
                  eduSubjectMapper.deleteByPrimaryKey(id);
              }
            }
        }
    }

    /**
     * 编辑商品类别
     * @param id
     * @return
     */
    @Override
    public EduSubjectVO edit(Long id) {
        EduSubject eduSubject = eduSubjectMapper.selectByPrimaryKey(id);
        return  EduSubjectConverter.converterToEduSubjectVO(eduSubject);
    }

    /**
     * 更新商品类别
     * @param id
     * @param eduSubjectVO
     */
    @Override
    public void update(Long id, EduSubjectVO eduSubjectVO) {
        EduSubject eduSubject = new EduSubject();
        BeanUtils.copyProperties(eduSubjectVO,eduSubject);
        eduSubject.setGmtModified(new Date());
        boolean flag = vidCategoryClient.updateSubjectById(eduSubjectVO);
        if(flag){
            eduSubjectMapper.updateByPrimaryKeySelective(eduSubject);
        }

    }

}
