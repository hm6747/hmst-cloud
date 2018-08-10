package com.syscloud.provider.user.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.syscloud.provider.user.mapper.SysRoleUserMapper;
import com.syscloud.provider.user.mapper.SysUserMapper;
import com.syscloud.provider.user.model.vo.SysRoleUser;
import com.syscloud.provider.user.model.vo.SysUser;
import com.syscloud.provider.user.service.SysLogService;
import com.syscloud.provider.user.service.SysRoleUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class SysRoleUserServiceImpl implements SysRoleUserService {

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysLogService sysLogService;

    @Override
    public List<SysUser> getListByRoleId(int roleId) {
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }
        return sysUserMapper.getByIdList(userIdList);
    }

    public void changeUserRoles(int userId, List<Integer> roleIdList) {
        List<Integer> originRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (isEqSet(originRoleIdList, roleIdList)) {
            return;
        }
        updateUserRoles(userId, roleIdList);
        sysLogService.saveRoleUserLog(userId, originRoleIdList, roleIdList,0);
    }

    public boolean isEqSet(List list1, List list2) {
        if (list1.size() == list2.size()) {
            Set<Integer> set1 = Sets.newHashSet(list1);
            Set<Integer> set2 = Sets.newHashSet(list2);
            set1.removeAll(set2);
            if (CollectionUtils.isEmpty(set1)) {
                return true;
            }
        }
        return false;
    }

    private void updateUserRoles(int userId, List<Integer> roleIdList) {
        sysRoleUserMapper.deleteByUserId(userId);

        if (CollectionUtils.isEmpty(roleIdList)) {
            return;
        }
        List<SysRoleUser> roleUserList = Lists.newArrayList();
        for (Integer roleId : roleIdList) {
            SysRoleUser roleUser = SysRoleUser.builder().roleId(roleId).userId(userId).operator("黄铭")
                    .operatorIp("127.0.0.1").operatorTime(new Date()).build();
            roleUserList.add(roleUser);
        }
        sysRoleUserMapper.batchInsert(roleUserList);
    }

  /*  public void changeRoleUsers(int roleId, List<Integer> userIdList) {
        List<Integer> originUserIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (originUserIdList.size() == userIdList.size()) {
            Set<Integer> originUserIdSet = Sets.newHashSet(originUserIdList);
            Set<Integer> userIdSet = Sets.newHashSet(userIdList);
            originUserIdSet.removeAll(userIdSet);
            if (CollectionUtils.isEmpty(originUserIdSet)) {
                return;
            }
        }
        updateRoleUsers(roleId, userIdList);
        saveRoleUserLog(roleId, originUserIdList, userIdList);
    }

    @Transactional
    private void updateRoleUsers(int roleId, List<Integer> userIdList) {
        sysRoleUserMapper.deleteByRoleId(roleId);

        if (CollectionUtils.isEmpty(userIdList)) {
            return;
        }
        List<SysRoleUser> roleUserList = Lists.newArrayList();
        for (Integer userId : userIdList) {
            SysRoleUser roleUser = SysRoleUser.builder().roleId(roleId).userId(userId).operator(RequestHolder.getCurrentUser().getUsername())
                    .operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())).operateTime(new Date()).build();
            roleUserList.add(roleUser);
        }
        sysRoleUserMapper.batchInsert(roleUserList);
    }*/
}
