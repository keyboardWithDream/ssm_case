package com.ssm.dao;

import com.ssm.domain.Permission;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/9/27
 */
@Repository
public interface IPermissionDao {

    /**
     * 通过角色 id 查询
     * @param id 角色 id
     * @return 资源权限
     * @throws Exception 异常
     */
    @Select("select * from permission where id in (select permissionId from role_permission where roleId = #{id})")
    List<Permission> findPermissionByRoleId(String id) throws Exception;

    /**
     * 查询所有资源权限
     * @return 资源权限list
     * @throws Exception 异常
     */
    @Select("select * from permission")
    List<Permission> findAll() throws Exception;

    /**
     * 资源权限添加
     * @param permission 资源权限信息
     * @throws Exception 异常
     */
    @Insert("insert into permission(permissionName, url) values (#{permissionName}, #{url})")
    void save(Permission permission) throws Exception;

    /**
     * 通过id查询资源权限
     * @param id 资源权限id
     * @return 资源权限信息
     * @throws Exception 异常
     */
    @Select("select * from permission where id = #{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "permissionName", column = "permissionName"),
            @Result(property = "url", column = "url"),
            @Result(property = "roles", column = "id", javaType = java.util.List.class, many = @Many(select = "com.ssm.dao.IRoleDao.findRoleByPermissionId"))
    })
    Permission findById(String id) throws Exception;


    /**
     * 通过id删除资源权限
     * @param id 资源权限id
     * @throws Exception 异常
     */
    @Delete("delete from permission where id = #{id}")
    void deleteById(String id) throws Exception;

    /**
     * 通资源id删除角色_资源权限信息
     * @param id 资源id
     * @throws Exception 异常
     */
    @Delete("delete from role_permission where permissionId = #{id}")
    void deletePermissionFromRolePermissionById(String id) throws Exception;
}
