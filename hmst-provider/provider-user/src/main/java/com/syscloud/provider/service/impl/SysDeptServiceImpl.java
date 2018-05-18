package com.syscloud.provider.service.impl;

import com.syscloud.exception.ParamException;
import com.syscloud.provider.mapper.SysDeptMapper;
import com.syscloud.provider.model.param.DeptParam;
import com.syscloud.provider.model.vo.SysDept;
import com.syscloud.provider.service.SysDeptService;
import com.syscloud.utils.BeanValidator;
import com.syscloud.utils.LevelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * Created by hm on 2017/12/23.
 */
@Slf4j
@Service
public class SysDeptServiceImpl implements SysDeptService {
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Override
    public void save(DeptParam param) throws  ParamException{
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级存在相同的名字的部门");
        }
        SysDept sysDept = SysDept.builder().name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).remark(param.getRemark()).build();
        sysDept.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        sysDept.setOperator("admin");//TO DO
        sysDept.setOperatorIp("127.0.0.1");
        sysDept.setOperatorTime(new Date());
        sysDeptMapper.insertSelective(sysDept);
    }

    @Override
    public void upDateDept(DeptParam param) throws  ParamException{
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级存在相同的名字的部门");
        }
        SysDept sysDeptBefore = sysDeptMapper.selectByPrimaryKey(param.getId());
        SysDept sysDept = SysDept.builder().name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).remark(param.getRemark()).id(param.getId()).build();
        sysDept.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        sysDept.setOperator("admin");//TO DO
        sysDept.setOperatorIp("127.0.0.1");
        sysDept.setOperatorTime(new Date());
        updateWithChild(sysDeptBefore, sysDept);
    }

    private void updateWithChild(SysDept before, SysDept after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())) {
            List<SysDept> deptList = sysDeptMapper.getChildListByLevel(before.getLevel()+"."+before.getId());
            if (CollectionUtils.isNotEmpty(deptList)) {
                for (SysDept dept : deptList) {
                    String level = dept.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevel(level);
                    }
                }
                sysDeptMapper.batchUpdateLevel(deptList);
            }
        }
        sysDeptMapper.updateByPrimaryKey(after);
    }

    private boolean checkExist(Integer parentId, String deptName, Integer deptId) {
        return sysDeptMapper.countByNameAndParentId(parentId, deptName, deptId) > 0;
    }

    private String getLevel(Integer deptId) {
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
        if (dept == null) {
            return null;
        }
        return dept.getLevel();
    }
}
