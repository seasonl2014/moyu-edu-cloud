package cn.xueden.system.converter;

import cn.xueden.common.entity.Department;
import cn.xueden.common.entity.User;
import cn.xueden.common.vo.UserVO;
import cn.xueden.system.mapper.DepartmentMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.swing.plaf.PanelUI;
import java.util.ArrayList;
import java.util.List;

/**功能描述：用户转换类
 * @Auther:http://www.xueden.cn
 * @Date:2020/7/22
 * @Description:cn.xueden.system.converter
 * @version:1.0
 */
@Component
public class UserConverter {

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 转换成VoList
     * @param users
     * @return
     */
    public List<UserVO> converterToUserVOList(List<User> users){
        List<UserVO> userVOS = new ArrayList<>();
        if(!CollectionUtils.isEmpty(users)){
            for(User user:users){
                UserVO userVO =  converterToUserVO(user);
                userVOS.add(userVO);
            }
        }
        return userVOS;
    }

    /**
     * 转VO
     * @param user
     * @return
     */
    public UserVO converterToUserVO(User user){
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        userVO.setStatus(user.getStatus()==0);
        Department department = departmentMapper.selectByPrimaryKey(user.getDepartmentId());
        if(department!=null&& department.getName()!=null){
            userVO.setDepartmentName(department.getName());
            userVO.setDepartmentId(department.getId());
        }

        return userVO;
    }

}
