package com.ssm.dao;

import com.ssm.domain.Permission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/9/27
 */
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
}
