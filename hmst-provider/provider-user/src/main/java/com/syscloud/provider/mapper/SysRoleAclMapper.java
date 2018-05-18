package com.syscloud.provider.mapper;

import com.syscloud.provider.model.example.SysRoleAclExample;
import com.syscloud.provider.model.vo.SysRoleAcl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SysRoleAclMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_acl
     *
     * @mbggenerated Thu Dec 21 23:20:45 CST 2017
     */
    int countByExample(SysRoleAclExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_acl
     *
     * @mbggenerated Thu Dec 21 23:20:45 CST 2017
     */
    int deleteByExample(SysRoleAclExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_acl
     *
     * @mbggenerated Thu Dec 21 23:20:45 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_acl
     *
     * @mbggenerated Thu Dec 21 23:20:45 CST 2017
     */
    int insert(SysRoleAcl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_acl
     *
     * @mbggenerated Thu Dec 21 23:20:45 CST 2017
     */
    int insertSelective(SysRoleAcl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_acl
     *
     * @mbggenerated Thu Dec 21 23:20:45 CST 2017
     */
    List<SysRoleAcl> selectByExample(SysRoleAclExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_acl
     *
     * @mbggenerated Thu Dec 21 23:20:45 CST 2017
     */
    SysRoleAcl selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_acl
     *
     * @mbggenerated Thu Dec 21 23:20:45 CST 2017
     */
    int updateByExampleSelective(@Param("record") SysRoleAcl record, @Param("example") SysRoleAclExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_acl
     *
     * @mbggenerated Thu Dec 21 23:20:45 CST 2017
     */
    int updateByExample(@Param("record") SysRoleAcl record, @Param("example") SysRoleAclExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_acl
     *
     * @mbggenerated Thu Dec 21 23:20:45 CST 2017
     */
    int updateByPrimaryKeySelective(SysRoleAcl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_acl
     *
     * @mbggenerated Thu Dec 21 23:20:45 CST 2017
     */
    int updateByPrimaryKey(SysRoleAcl record);
    List<Integer> getAclIdListByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);

    void deleteByRoleId(@Param("roleId") int roleId);

    void batchInsert(@Param("roleAclList") List<SysRoleAcl> roleAclList);

    List<Integer> getRoleIdListByAclId(@Param("aclId") int aclId);
}