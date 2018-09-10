package com.syscloud.provider.user.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.syscloud.provider.user.mapper.SysAclMapper;
import com.syscloud.provider.user.mapper.SysAclModuleMapper;
import com.syscloud.provider.user.mapper.SysDeptMapper;
import com.syscloud.provider.user.mapper.SysRoleMapper;
import com.syscloud.provider.user.model.dto.AclDto;
import com.syscloud.provider.user.model.dto.AclModuleLevelDto;
import com.syscloud.provider.user.model.dto.SysDeptDto;
import com.syscloud.provider.user.model.dto.SysRoleDto;
import com.syscloud.provider.user.model.vo.SysAcl;
import com.syscloud.provider.user.model.vo.SysAclModule;
import com.syscloud.provider.user.model.vo.SysDept;
import com.syscloud.provider.user.model.vo.SysRole;
import com.syscloud.provider.user.service.SysCoreService;
import com.syscloud.provider.user.service.SysTreesService;
import com.syscloud.utils.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hm on 2017/12/23.
 */
@Service
public class SysTreesServiceImpl implements SysTreesService {
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Resource
    private SysAclModuleMapper sysAclModuleMapper;
    @Autowired
    private SysAclMapper sysAclMapper;
    @Autowired
    private SysCoreService sysCoreService;
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<AclModuleLevelDto> roleTree(int roleId) {
        int userId = 1;
        // 1、当前用户已分配的权限点
        List<SysAcl> userAclList = sysCoreService.getCurrentUserAclList(userId);
        // 2、当前角色分配的权限点
        List<SysAcl> roleAclList = sysCoreService.getRoleAclList(roleId);

        List<AclDto> aclDtoList = Lists.newArrayList();

        Set<Integer> userAclIdSet = new HashSet<>();
        for (SysAcl acl :
                userAclList) {
            userAclIdSet.add(acl.getId());
        }
        Set<Integer> roleAclIdSet = new HashSet<>();
        for (SysAcl acl :
                roleAclList) {
            roleAclIdSet.add(acl.getId());
        }
        // 3、当前系统所有权限点
        List<SysAcl> allAclList = sysAclMapper.getAll();
        for (SysAcl acl : allAclList) {
            AclDto dto = AclDto.adapt(acl);
            if (userAclIdSet.contains(acl.getId())) {
                dto.setHasAcl(true);
            }
            if (roleAclIdSet.contains(acl.getId())) {
                dto.setChecked(true);
            }
            aclDtoList.add(dto);
        }
        return aclListToTree(aclDtoList);
    }

    @Override
    public List<SysRoleDto> roleUserTree(int selectUserId, int userId) {
        // 1、当前用户已分配角色
        List<SysRole> userRoleList = sysCoreService.getCurrentUserRoleList(userId);
        // 2、选中用户已分配角色
        List<SysRole> selUserRoleList = sysCoreService.getCurrentUserRoleList(selectUserId);
        Set<Integer> userRoleIdSet = new HashSet<>();
        for (SysRole role :
                userRoleList) {
            userRoleIdSet.add(role.getId());
        }
        Set<Integer> selUserRoleIdSet = new HashSet<>();
        for (SysRole role :
                selUserRoleList) {
            selUserRoleIdSet.add(role.getId());
        }
        // 3、当前系统所有角色
        List<SysRole> allRoleList = sysRoleMapper.getAll(null, null);
        List<SysRoleDto> roleDtoList = Lists.newArrayList();
        for (SysRole role :allRoleList) {
            SysRoleDto dto = SysRoleDto.adapt(role);
            if(userRoleIdSet.contains(role.getId())){
                dto.setHasAcl(true);
            }
            if(selUserRoleIdSet.contains(role.getId())){
                dto.setChecked(true);
            }
            roleDtoList.add(dto);
        }
        return roleDtoList;
    }

    public List<AclModuleLevelDto> aclListToTree(List<AclDto> aclDtoList) {
        if (CollectionUtils.isEmpty(aclDtoList)) {
            return Lists.newArrayList();
        }
        List<AclModuleLevelDto> aclModuleLevelList = aclModuleTree();

        Multimap<Integer, AclDto> moduleIdAclMap = ArrayListMultimap.create();
        for (AclDto acl : aclDtoList) {
            if (acl.getStatus() == 1) {
                moduleIdAclMap.put(acl.getAclModuleId(), acl);
            }
        }
        bindAclsWithOrder(aclModuleLevelList, moduleIdAclMap);
        return aclModuleLevelList;
    }

    public void bindAclsWithOrder(List<AclModuleLevelDto> aclModuleLevelList, Multimap<Integer, AclDto> moduleIdAclMap) {
        if (CollectionUtils.isEmpty(aclModuleLevelList)) {
            return;
        }
        for (AclModuleLevelDto dto : aclModuleLevelList) {
            List<AclDto> aclDtoList = (List<AclDto>) moduleIdAclMap.get(dto.getId());
            if (CollectionUtils.isNotEmpty(aclDtoList)) {
                dto.setAclList(aclDtoList);
            }
            bindAclsWithOrder(dto.getAclModuleList(), moduleIdAclMap);
        }
    }

    @Override
    public List<AclModuleLevelDto> aclModuleTree() {
        List<SysAclModule> aclModuleList = sysAclModuleMapper.getAllAclModule();
        List<AclModuleLevelDto> dtoList = Lists.newArrayList();
        for (SysAclModule aclModule : aclModuleList) {
            dtoList.add(AclModuleLevelDto.adapt(aclModule));
        }
        return aclModuleListToTree(dtoList);
    }

    public List<AclModuleLevelDto> aclModuleListToTree(List<AclModuleLevelDto> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return Lists.newArrayList();
        }
        // level -> [aclmodule1, aclmodule2, ...] Map<String, List<Object>>
        Multimap<String, AclModuleLevelDto> levelAclModuleMap = ArrayListMultimap.create();
        List<AclModuleLevelDto> rootList = Lists.newArrayList();

        for (AclModuleLevelDto dto : dtoList) {
            levelAclModuleMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        transformAclModuleTree(rootList, LevelUtil.ROOT, levelAclModuleMap);
        return rootList;
    }

    public void transformAclModuleTree(List<AclModuleLevelDto> dtoList, String level, Multimap<String, AclModuleLevelDto> levelAclModuleMap) {
        for (int i = 0; i < dtoList.size(); i++) {
            AclModuleLevelDto dto = dtoList.get(i);
            String nextLevel = LevelUtil.calculateLevel(level, dto.getId());
            List<AclModuleLevelDto> tempList = (List<AclModuleLevelDto>) levelAclModuleMap.get(nextLevel);
            List<SysAcl> aclList= sysAclMapper.getPageByAclModuleId(dto.getId(),null,null,1);
            List<AclDto> aclDtoList= Lists.newArrayList();
            for (SysAcl acl : aclList) {
                aclDtoList.add(AclDto.adapt(acl));
            }
            dto.setAclList(aclDtoList);
            if (CollectionUtils.isNotEmpty(tempList)) {
                dto.setAclModuleList(tempList);
                transformAclModuleTree(tempList, nextLevel, levelAclModuleMap);
            }
        }
    }

    @Override
    public List<SysDeptDto> deptTree() {
        List<SysDept> deptList = sysDeptMapper.getAllDept();
        List<SysDeptDto> dtoList = Lists.newArrayList();
        for (SysDept dept :
                deptList) {
            SysDeptDto dto = SysDeptDto.adapt(dept);
            dtoList.add(dto);
        }
        List<SysDeptDto> dtoTreeList = deptListToTree(dtoList);
        return dtoTreeList;
    }

    public List<SysDeptDto> deptListToTree(List<SysDeptDto> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return Lists.newArrayList();
        }
        Multimap<String, SysDeptDto> dtoMap = ArrayListMultimap.create();
        List<SysDeptDto> rootList = Lists.newArrayList();
        for (SysDeptDto dto :
                dtoList) {
            dtoMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
/*        Collections.sort(rootList, new Comparator<SysDeptDto>() {
            @Override
            public int compare(SysDeptDto o1, SysDeptDto o2) {
                return o1.getSeq()-o2.getSeq();
            }
        });*/
        // 递归生成树
        transformDeptTree(rootList, LevelUtil.ROOT, dtoMap);
        return rootList;
    }

    // level:0, 0, all 0->0.1,0.2
    // level:0.1
    // level:0.2
    public void transformDeptTree(List<SysDeptDto> deptLevelList, String level, Multimap<String, SysDeptDto> levelDeptMap) {
        for (int i = 0; i < deptLevelList.size(); i++) {
            // 遍历该层的每个元素
            SysDeptDto deptLevelDto = deptLevelList.get(i);
            // 处理当前层级的数据
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            // 处理下一层
            List<SysDeptDto> tempDeptList = (List<SysDeptDto>) levelDeptMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempDeptList)) {
                // 设置下一层部门
                deptLevelDto.setDeptDtoList(tempDeptList);
                // 进入到下一层处理
                transformDeptTree(tempDeptList, nextLevel, levelDeptMap);
            }
        }
    }

}
