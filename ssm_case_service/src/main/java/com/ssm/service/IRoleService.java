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
     * @return 角色列表
     * @throws Exception 异常
     */
    List<Role> findAll() throws Exception;
}
