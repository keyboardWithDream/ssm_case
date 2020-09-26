package com.ssm.service;

import com.ssm.domain.Role;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/9/26
 */
public interface IRoleService {

    /**
     * 查询所有角色
     * @param page 当前页码
     * @param size 每页显示条数
     * @return 角色列表
     * @throws Exception 异常
     */
    List<Role> findAll(int page, int size) throws Exception;


    /**
     * 保存角色
     * @param role 角色信息
     * @throws Exception 异常
     */
    void save(Role role) throws Exception;
}
