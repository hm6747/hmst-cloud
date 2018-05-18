package com.syscloud.provider.mapper;

import com.syscloud.provider.model.example.SysAclExample;
import com.syscloud.provider.model.query.PageQuery;
import com.syscloud.provider.model.vo.SysAcl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SysAclMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_acl
     *
     * @mbggenerated Thu Dec 21 23:20:45 CST 2017
     */
    int countByExample(SysAclExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_acl
     *
     * @mbggenerated Thu Dec 21 23:20:45 CST 2017
     */
    int deleteByExample(SysAclExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_acl
     *
     * @mbggenerated Thu Dec 21 23:20:45 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_acl
     *
     * @mbggenerated Thu Dec 21 23:20:45 CST 2017
     */
    int insert(SysAcl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_acl
     *
     * @mbggenerated Thu Dec 21 23:20:45 CST 2017
     */
    int insertSelective(SysAcl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_acl
     *
     * @mbggenerated Thu Dec 21 23:20:45 CST 2017
     */
    List<SysAcl> selectByExample(SysAclExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_acl
     *
     * @mbggenerated Thu Dec 21 23:20:45 CST 2017
     */
    SysAcl selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_acl
     *
     * @mbggenerated Thu Dec 21 23:20:45 CST 2017
     */
    int updateByExampleSelective(@Param("record") SysAcl record, @Param("example") SysAclExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_acl
     *
     * @mbggenerated Thu Dec 21 23:20:45 CST 2017
     */
    int updateByExample(@Param("record") SysAcl record, @Param("example") SysAclExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_acl
     *
     * @mbggenerated Thu Dec 21 23:20:45 CST 2017
     */
    int updateByPrimaryKeySelective(SysAcl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_acl
     *
     * @mbggenerated Thu Dec 21 23:20:45 CST 2017
     */
    int updateByPrimaryKey(SysAcl record);

    int countByAclModuleId(@Param("aclModuleId") int aclModuleId);

    List<SysAcl> getPageByAclModuleId(@Param("aclModuleId") int aclModuleId, @Param("page") PageQuery page, @Param("keyword") String keyword);

    int countByNameAndAclModuleId(@Param("aclModuleId") int aclModuleId, @Param("name") String name, @Param("id") Integer id);

    List<SysAcl> getAll();

    List<SysAcl> getByIdList(@Param("idList") List<Integer> idList);

    List<SysAcl> getByUrl(@Param("url") String url);
}