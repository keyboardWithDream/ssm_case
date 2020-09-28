package com.ssm.dao;

import com.ssm.domain.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/9/26
 */
@Repository
public interface IRoleDao {

    /**
     * 通过用户id查询角色
     * @param id 用户id
     * @return 用户角色
     * @throws Exception 异常
     */
    @Select("select * from role where id in (select roleId from users_role where userId = #{id})")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "roleName", column = "roleName"),
            @Result(property = "roleDesc", column = "roleDesc"),
            @Result(property = "permissions", column = "id", javaType = java.util.List.class, many = @Many(select = "com.ssm.dao.IPermissionDao.findPermissionByRoleId"))
    })
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

    /**
     * 通过资源权限id查询角色
     * @param id 资源权限id
     * @return 角色list
     * @throws Exception 异常
     */
    @Select("select * from role where id in (select roleId from role_permission where permissionId = #{id})")
    List<Role> findRoleByPermissionId(String id) throws Exception;


    /**
     * 通过id查询角色详情
     * @param id 角色id
     * @return 角色详情
     */
    @Select("select * from role where id = #{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "roleName", column = "roleName"),
            @Result(property = "roleDesc", column = "roleDesc"),
            @Result(property = "permissions", column = "id", javaType = java.util.List.class,many = @Many(select = "com.ssm.dao.IPermissionDao.findPermissionByRoleId")),
            @Result(property = "userInfos", column = "id", javaType = java.util.List.class, many = @Many(select = "com.ssm.dao.IUserDao.findUserByRoleId"))
    })
    Role findById(String id);


    /**
     * 通过id删除角色
     * @param id 角色id
     */
    @Delete("delete from role where id = #{id}")
    void deleteFromRoleById(String id);


    /**
     * 通过id从user_role表中删除
     * @param id 角色id
     */
    @Delete("delete from users_role where roleId = #{id}")
    void deleteFromUsersRoleById(String id);


    /**
     * 从角色权限表中删除角色
     * @param id 角色id
     */
    @Delete("delete from role_permission where roleId =#{id}")
    void deleteFromRolePermissionById(String id);
}
