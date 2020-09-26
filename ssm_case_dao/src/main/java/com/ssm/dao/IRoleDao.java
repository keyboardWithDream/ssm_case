package com.ssm.dao;

import com.ssm.domain.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/9/26
 */
public interface IRoleDao {

    /**
     * 通过用户id查询角色
     * @param id 用户id
     * @return 用户角色
     * @throws Exception 异常
     */
    @Select("select * from role where id in (select roleId from users_role where userId = #{id})")
    List<Role> findRoleByUserId(String id) throws Exception;

    /**
     * 查询所有角色
     * @return 角色list
     * @throws Exception 异常
     */
    @Select("select * from role")
    List<Role> findAll() throws Exception;

    /**
     * 保存角色信息
     * @param role 角色信息
     * @throws Exception 异常
     */
    @Insert("insert into role values(#{id}, #{roleName}, #{roleDesc})")
    void save(Role role) throws Exception;
}
