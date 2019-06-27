package com.cims.business.user.service.impl;

import com.cims.business.card.entity.UserWarehouse;
import com.cims.business.card.mapper.UserWarehouseMapper;
import com.cims.business.user.entity.Role;
import com.cims.business.user.entity.User;
import com.cims.business.user.mapper.UserMapper;
import com.cims.business.user.service.UserService;
import com.cims.business.user.vo.UserConditionVO;
import com.cims.business.user.vo.UserInfoVO;
import com.cims.business.user.vo.UserRoleVO;
import com.cims.business.user.vo.UserVO;
import com.cims.common.context.UserContext;
import com.cims.common.util.RSAUtils;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;
import com.cims.framework.page.PageHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author baidu
 * @date 2019/4/14 下午12:50
 * @description
 **/

@Service
public class UserServiceImpl implements UserService {

    private static final String INITIAL_PASSWORD = "123456";
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserWarehouseMapper warehouseMapper;
    @Override
    public PageDataResult<UserRoleVO> list(UserConditionVO conditionVO, PageBean pageBean) {
        PageHelp.pageAndOrderBy(pageBean);
        List<UserRoleVO> list = userMapper.list(conditionVO);
        return PageHelp.getDataResult(list,pageBean);
    }

    @Override
    public User getUserById(Long userId) {
        return userMapper.getUser(userId,null);
    }

    @Override
    public User getUserByUsername(String userName) {
        return userMapper.getUser(null,userName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(UserVO userVO) throws BizException {
        String username = userVO.getUsername();
        //查询用户名是否有重复
        User useExist = getUserByUsername(username);
        if (useExist != null) {
            throw new BizException("用户名已经存在");
        }

        String password = RSAUtils.getDecrypted(userVO.getPassword());

        User user = new User();
        String secretKey = passwordEncoder.encode(password);
        user.setUsername(username);
        user.setPassword(secretKey);
        user.setNickname(userVO.getNickname());
        user.setCreateUserId(UserContext.getUserId());
        user.setCreateTime(new Date());

        userMapper.addUser(user);

        saveUserRole(user.getId(),userVO.getRoleIds());

        saveUserWarehouse(user.getId(),userVO.getWarehouseIds());

    }

    @Override
    public List<Role> listRoles(){
        return userMapper.listRoles();
    }


    @Override
    public UserInfoVO getUserByUserId(Long userId){
        return userMapper.getUserByUserId(userId);
    }


    @Override
    public void disableBatchUser(List<Long> ids) {
        Long userId = UserContext.getUserId();
        userMapper.batchDisableUser(ids,userId);
    }


    @Override
    public void enableBatchUser(List<Long> ids) {
        Long userId = UserContext.getUserId();
        userMapper.batchEnableUser(ids,userId);
    }


    @Override
    public void deleteBatchUser(List<Long> ids)  {
        Long userId = UserContext.getUserId();
        userMapper.batchDeleteUser(ids,userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserVO userVO) throws BizException {
        User user = new User();
        user.setId(userVO.getId());
        user.setUsername(userVO.getUsername());
        user.setNickname(userVO.getNickname());
        user.setUpdateUserId(UserContext.getUserId());

        User u = userMapper.getUser(user.getId(),null);
        if (u == null){
            throw new BizException("用户不存在");
        }
        userMapper.updateUser(user);

        saveUserRole(userVO.getId(),userVO.getRoleIds());

        saveUserWarehouse(userVO.getId(),userVO.getWarehouseIds());

    }

    @Override
    public void updateUserPassword(User user) {
        userMapper.updateUserPassword(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Long id) throws BizException {
        User user = userMapper.getUser(id,null);
        if (user == null) {
            throw new BizException("用户不存在");
        }

        String secretKey = passwordEncoder.encode(INITIAL_PASSWORD);
        user.setPassword(secretKey);
        updateUserPassword(user);
    }

    /**
     * 用户角色关系保存
     * @param userId
     * @param roleIds
     */
    private void saveUserRole(Long userId,List<Long> roleIds){
        userMapper.deleteRoleByUserId(userId);
        roleIds.stream().forEach(roleId ->{
            userMapper.addUserRole(userId,roleId);
        });
    }

    /**
     * 用户仓库关系保存
     * @param userId
     * @param warehouseIds
     */
    private void saveUserWarehouse(Long userId,List<Long> warehouseIds){
        warehouseMapper.deleteByUserId(userId);
        if(!CollectionUtils.isEmpty(warehouseIds)) {
            warehouseIds.stream().forEach(warehouseId -> {
                UserWarehouse record = new UserWarehouse();
                record.setUserId(userId);
                record.setWarehouseId(warehouseId);
                warehouseMapper.insert(record);
            });
        }
    }
}
