package com.ssm.service.impl;

import com.ssm.dao.IRoleDao;
import com.ssm.domain.Role;
import com.ssm.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @Author Harlan
 * @Date 2020/9/26
 */
@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleDao dao;

    @Override
    public List<Role> findAll() throws Exception{
        return dao.findAll();
    }

    @Override
    public void save(Role role) throws Exception {
        role.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        dao.save(role);
    }
}
