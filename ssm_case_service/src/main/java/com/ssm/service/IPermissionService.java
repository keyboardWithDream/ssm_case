package com.ssm.service;

import com.ssm.domain.Permission;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/9/27
 */
public interface IPermissionService {

    /**
     * 查询所有资源权限
     * @return 资源权限list
     * @throws Exception 异常
     * @param page 当前页码
     * @param size 每页条数
     */
    List<Permission> findAll(int page, int size) throws Exception;


    /**
     * 添加资源权限
     * @param permission 资源权限信息
     * @throws Exception 异常
     */
    void save(Permission permission) throws Exception;

    /**
     * 通过id查询资源权限
     * @param id 资源权限id
     * @return 资源权限信息
     * @throws Exception 异常
     */
    Permission findById(String id) throws Exception;


    /**
     * 通过id删除资源权限
     * @param id 资源权限id
     * @throws Exception 异常
     */
    void deleteById(String id) throws Exception;
}
