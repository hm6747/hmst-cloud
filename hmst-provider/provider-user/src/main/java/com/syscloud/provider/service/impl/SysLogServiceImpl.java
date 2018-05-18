package com.syscloud.provider.service.impl;

import com.google.common.base.Preconditions;
import com.syscloud.exception.ParamException;
import com.syscloud.provider.mapper.*;
import com.syscloud.provider.model.dto.SearchLogDto;
import com.syscloud.provider.model.param.LogType;
import com.syscloud.provider.model.param.SearchLogParam;
import com.syscloud.provider.model.query.PageQuery;
import com.syscloud.provider.model.query.PageResult;
import com.syscloud.provider.model.vo.*;
import com.syscloud.provider.service.SysLogService;
import com.syscloud.provider.service.SysRoleAclService;
import com.syscloud.provider.service.SysRoleUserService;
import com.syscloud.utils.BeanValidator;
import com.syscloud.utils.JsonMapper;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService {
    @Resource
    private SysLogMapper sysLogMapper;

    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysAclModuleMapper sysAclModuleMapper;
    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRoleAclService sysRoleAclService;
    @Resource
    private SysRoleUserService sysRoleUserService;

    public void recover(int id) throws ParamException {
        SysLogWithBLOBs sysLog = sysLogMapper.selectByPrimaryKey(id);
        Preconditions.checkNotNull(sysLog, "待还原的记录不存在");
        switch (sysLog.getType()) {
            case LogType.TYPE_DEPT:
                SysDept beforeDept = sysDeptMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeDept, "待还原的部门已经不存在了");
                if (StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new ParamException("新增和删除操作不做还原");
                }
                SysDept afterDept = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysDept>() {
                });
                afterDept.setOperator("黄铭");
                afterDept.setOperatorIp("127.0.0.1");
                afterDept.setOperatorTime(new Date());
                sysDeptMapper.updateByPrimaryKeySelective(afterDept);
                saveDeptLog(beforeDept, afterDept,1);
                break;
            case LogType.TYPE_USER:
                SysUser beforeUser = sysUserMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeUser, "待还原的用户已经不存在了");
                if (StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new ParamException("新增和删除操作不做还原");
                }
                SysUser afterUser = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysUser>() {
                });
                afterUser.setOperator("黄铭");
                afterUser.setOperatorIp("127.0.0.1");
                afterUser.setOperatorTime(new Date());
                sysUserMapper.updateByPrimaryKeySelective(afterUser);
                saveUserLog(beforeUser, afterUser,1);
                break;
            case LogType.TYPE_ACL_MODULE:
                SysAclModule beforeAclModule = sysAclModuleMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeAclModule, "待还原的权限模块已经不存在了");
                if (StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new ParamException("新增和删除操作不做还原");
                }
                SysAclModule afterAclModule = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysAclModule>() {
                });
                afterAclModule.setOperator("黄铭");
                afterAclModule.setOperatorIp("127.0.0.1");
                afterAclModule.setOperatorTime(new Date());
                sysAclModuleMapper.updateByPrimaryKeySelective(afterAclModule);
                saveAclModuleLog(beforeAclModule, afterAclModule,1);
                break;
            case LogType.TYPE_ACL:
                SysAcl beforeAcl = sysAclMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeAcl, "待还原的权限点已经不存在了");
                if (StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new ParamException("新增和删除操作不做还原");
                }
                SysAcl afterAcl = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysAcl>() {
                });
                afterAcl.setOperator("黄铭");
                afterAcl.setOperatorIp("127.0.0.1");
                afterAcl.setOperatorTime(new Date());
                sysAclMapper.updateByPrimaryKeySelective(afterAcl);
                saveAclLog(beforeAcl, afterAcl,1);
                break;
            case LogType.TYPE_ROLE:
                SysRole beforeRole = sysRoleMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeRole, "待还原的角色已经不存在了");
                if (StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new ParamException("新增和删除操作不做还原");
                }
                SysRole afterRole = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysRole>() {
                });
                afterRole.setOperator("黄铭");
                afterRole.setOperatorIp("127.0.0.1");
                afterRole.setOperatorTime(new Date());
                sysRoleMapper.updateByPrimaryKeySelective(afterRole);
                saveRoleLog(beforeRole, afterRole,1);
                break;
            case LogType.TYPE_ROLE_ACL:
                SysRole aclRole = sysRoleMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(aclRole, "角色已经不存在了");
                sysRoleAclService.changeRoleAcls(sysLog.getTargetId(), JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<List<Integer>>() {
                }));
                saveRoleAclLog(sysLog.getTargetId(),JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<List<Integer>>() {
                }),JsonMapper.string2Obj(sysLog.getNewValue(), new TypeReference<List<Integer>>() {
                }),1);
                break;
            case LogType.TYPE_ROLE_USER:
                SysUser userRole = sysUserMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(userRole, "用户已经不存在了");
                sysRoleUserService.changeUserRoles(sysLog.getTargetId(), JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<List<Integer>>() {
                }));
                saveRoleUserLog(sysLog.getTargetId(), JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<List<Integer>>() {
                }),JsonMapper.string2Obj(sysLog.getNewValue(), new TypeReference<List<Integer>>() {
                }),1);
                break;
            default:
                ;
        }
    }

    @Override
    public PageResult<SysLogWithBLOBs> searchPageList(SearchLogParam param, PageQuery page) throws  ParamException{
        BeanValidator.check(page);
        SearchLogDto dto = new SearchLogDto();
        dto.setType(param.getType());
        if (StringUtils.isNotBlank(param.getBeforeSeg())) {
            dto.setBeforeSeg("%" + param.getBeforeSeg() + "%");
        }
        if (StringUtils.isNotBlank(param.getAfterSeg())) {
            dto.setAfterSeg("%" + param.getAfterSeg() + "%");
        }
        if (StringUtils.isNotBlank(param.getOperator())) {
            dto.setOperator("%" + param.getOperator() + "%");
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (StringUtils.isNotBlank(param.getFromTime())) {
                dto.setFromTime(dateFormat.parse(param.getFromTime()));
            }
            if (StringUtils.isNotBlank(param.getToTime())) {
                dto.setToTime(dateFormat.parse(param.getToTime()));
            }
        } catch (Exception e) {
            throw new ParamException("传入的日期格式有问题，正确格式为：yyyy-MM-dd HH:mm:ss");
        }
        int count = sysLogMapper.countBySearchDto(dto);
        if (count > 0) {
            List<SysLogWithBLOBs> logList = sysLogMapper.getPageListBySearchDto(dto, page);
            return PageResult.<SysLogWithBLOBs>builder().total(count).data(logList).build();
        }
        return PageResult.<SysLogWithBLOBs>builder().build();
    }

    @Override
    public void saveDeptLog(SysDept before, SysDept after,Integer status) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_DEPT);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator("黄铭");
        sysLog.setOperatorIp("127.0.0.1");
        sysLog.setOperatorTime(new Date());
        sysLog.setStatus(status);
        sysLogMapper.insertSelective(sysLog);
    }

    @Override
    public void saveUserLog(SysUser before, SysUser after,Integer status) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_USER);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator("黄铭");
        sysLog.setOperatorIp("127.0.0.1");
        sysLog.setOperatorTime(new Date());
        sysLog.setStatus(status);
        sysLogMapper.insertSelective(sysLog);
    }

    @Override
    public void saveAclModuleLog(SysAclModule before, SysAclModule after,Integer status) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ACL_MODULE);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator("黄铭");
        sysLog.setOperatorIp("127.0.0.1");
        sysLog.setOperatorTime(new Date());
        sysLog.setStatus(status);
        sysLogMapper.insertSelective(sysLog);
    }

    @Override
    public void saveAclLog(SysAcl before, SysAcl after,Integer status) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ACL);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator("黄铭");
        sysLog.setOperatorIp("127.0.0.1");
        sysLog.setOperatorTime(new Date());
        sysLog.setStatus(status);
        sysLogMapper.insertSelective(sysLog);
    }

    @Override
    public void saveRoleLog(SysRole before, SysRole after,Integer status) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator("黄铭");
        sysLog.setOperatorIp("127.0.0.1");
        sysLog.setOperatorTime(new Date());
        sysLog.setStatus(status);
        sysLogMapper.insertSelective(sysLog);
    }

    @Override
    public void saveRoleUserLog(int userId, List<Integer> before, List<Integer> after,Integer status) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE_USER);
        sysLog.setTargetId(userId);
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator("黄铭");
        sysLog.setOperatorIp("127.0.0.1");
        sysLog.setOperatorTime(new Date());
        sysLog.setStatus(status);
        sysLogMapper.insertSelective(sysLog);
    }

    @Override
    public void saveRoleAclLog(int roleId, List<Integer> before, List<Integer> after,Integer status) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE_ACL);
        sysLog.setTargetId(roleId);
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator("黄铭");
        sysLog.setOperatorIp("127.0.0.1");
        sysLog.setOperatorTime(new Date());
        sysLog.setStatus(status);
        sysLogMapper.insertSelective(sysLog);
    }
}
