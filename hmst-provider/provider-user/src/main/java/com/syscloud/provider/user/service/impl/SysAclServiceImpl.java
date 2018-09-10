package com.syscloud.provider.user.service.impl;

import com.google.common.base.Preconditions;
import com.syscloud.exception.ParamException;
import com.syscloud.provider.user.mapper.SysAclMapper;
import com.syscloud.provider.user.model.param.AclParam;
import com.syscloud.provider.user.model.query.PageQuery;
import com.syscloud.provider.user.model.query.PageResult;
import com.syscloud.provider.user.model.vo.SysAcl;
import com.syscloud.provider.user.service.SysAclService;
import com.syscloud.provider.user.service.SysLogService;
import com.syscloud.utils.BeanValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SysAclServiceImpl implements SysAclService {

    @Autowired
    private SysAclMapper sysAclMapper;
          @Resource
        private SysLogService sysLogService;
    @Override
    public void save(AclParam param) throws  ParamException{
        BeanValidator.check(param);
        if (checkExist(param.getAclModuleId(), param.getName(), param.getId())) {
            throw new ParamException("当前权限模块下面存在相同名称的权限点");
        }
        SysAcl acl = SysAcl.builder().name(param.getName()).aclModuleId(param.getAclModuleId()).url(param.getUrl())
                .type(param.getType()).status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark()).build();
        acl.setCode(generateCode());
        acl.setOperator("admin"/*RequestHolder.getCurrentUser().getUsername()*/);
        acl.setOperatorTime(new Date());
        acl.setOperatorIp("127.0.0.1");
        sysAclMapper.insertSelective(acl);
        sysLogService.saveAclLog(null, acl,0);
    }

    @Override
    public void update(AclParam param) throws  ParamException{
        BeanValidator.check(param);
        if (checkExist(param.getAclModuleId(), param.getName(), param.getId())) {
            throw new ParamException("当前权限模块下面存在相同名称的权限点");
        }
        SysAcl before = sysAclMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的权限点不存在");

        SysAcl after = SysAcl.builder().id(param.getId()).name(param.getName()).aclModuleId(param.getAclModuleId()).url(param.getUrl())
                .type(param.getType()).status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark()).build();
        after.setOperator("admin");
        after.setOperatorTime(new Date());
        after.setOperatorIp("127.0.0.1");

        sysAclMapper.updateByPrimaryKeySelective(after);
        sysLogService.saveAclLog(before, after,0);
    }

    public boolean checkExist(int aclModuleId, String name, Integer id) {
        return sysAclMapper.countByNameAndAclModuleId(aclModuleId, name, id) > 0;
    }

    public String generateCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date()) + "_" + (int) (Math.random() * 100);
    }

    @Override
    public PageResult<SysAcl> getPageByAclModuleId(int aclModuleId, PageQuery page, String keyword) throws  ParamException{
        BeanValidator.check(page);
        int count = sysAclMapper.countByAclModuleId(aclModuleId);
        if (count > 0) {
            List<SysAcl> aclList = sysAclMapper.getPageByAclModuleId(aclModuleId, page,keyword,-1);
            return PageResult.<SysAcl>builder().data(aclList).total(count).build();
        }
        return PageResult.<SysAcl>builder().build();
    }
}
