package com.syscloud.provider.user.service.impl;

import com.google.common.base.Preconditions;
import com.syscloud.exception.ParamException;
import com.syscloud.provider.user.mapper.SysUserMapper;
import com.syscloud.provider.user.model.param.UserParam;
import com.syscloud.provider.user.model.query.PageQuery;
import com.syscloud.provider.user.model.query.PageResult;
import com.syscloud.provider.user.model.vo.SysUser;
import com.syscloud.provider.user.service.SysLogService;
import com.syscloud.provider.user.service.SysUserService;
import com.syscloud.utils.BeanValidator;
import com.syscloud.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by hm on 2017/12/25.
 */
@Service
public class SysUserServiceImpl  implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysLogService sysLogService;
    @Override
    public void save(UserParam param) throws  ParamException{
        BeanValidator.check(param);
        if (checkTelphoneExist(param.getTelephone(),param.getId())){
            throw new ParamException("电话已被占用");
        }
        if (checkEmailExist(param.getMail(),param.getId())){
            throw new ParamException("邮箱已被占用");
        }
        String password = "12345678";
        String encryptedPassword = MD5Util.encrypt(password);
        SysUser user = SysUser.builder().username(param.getUsername()).telephone(param.getTelephone()).mail(param.getMail())
                .password(encryptedPassword).deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();
        user.setOperatorTime(new Date());
        user.setOperator("黄铭");
        user.setOperatorIp("127.0.0.1");
        sysUserMapper.insertSelective(user);
        sysLogService.saveUserLog(null,user,0);
    }

    public void update(UserParam param)throws  ParamException{
        BeanValidator.check(param);
        if (checkTelphoneExist(param.getTelephone(),param.getId())){
            throw new ParamException("电话已被占用");
        }
        if (checkEmailExist(param.getMail(),param.getId())){
            throw new ParamException("邮箱已被占用");
        }
        SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"待更新的用户不存在");
        SysUser after = SysUser.builder().id(param.getId()).username(param.getUsername()).telephone(param.getTelephone()).mail(param.getMail())
                .deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();
        after.setOperatorTime(new Date());
        after.setOperator("黄铭");
        after.setOperatorIp("127.0.0.1");
        sysUserMapper.updateByPrimaryKeySelective(after);
        sysLogService.saveUserLog(before,after,0);
    }

    public boolean checkEmailExist(String mail,Integer userId){
        return sysUserMapper.conuntByMail(mail,userId)>0;
    }

    public boolean checkTelphoneExist(String telephone,Integer userId){
        return sysUserMapper.conuntByTelphone(telephone,userId)>0;
    }
    @Override
    public SysUser findByKeyword(String keyword){
        return  sysUserMapper.findByKeword(keyword);
    }

    @Override
    public SysUser findById(Integer id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery page, String keyword) throws  ParamException{
        BeanValidator.check(page);
        int count = sysUserMapper.conuntByDeptId(deptId);
        if (count > 0) {
            List<SysUser> list = sysUserMapper.getPageByDeptId(deptId, page,keyword);
            return PageResult.<SysUser>builder().total(count).data(list).build();
        }
        return PageResult.<SysUser>builder().build();
    }
}
