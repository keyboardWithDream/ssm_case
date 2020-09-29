package com.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.ssm.dao.IRoleDao;

import com.ssm.domain.Permission;
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
    public List<Role> findAll(int page, int size) throws Exception{
        PageHelper.startPage(page, size);
        return dao.findAll();
    }

    @Override
    public void save(Role role) throws Exception {
        role.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        dao.save(role);
    }

    @Override
    public Role findById(String id) throws Exception {
        return dao.findById(id);
    }

    @Override
    public void deleteById(String id) throws Exception {
        dao.deleteFromRolePermissionById(id);
        dao.deleteFromUsersRoleById(id);
        dao.deleteFromRoleById(id);
    }

    @Override
    public List<Permission> findOtherPermissionById(String id) throws Exception {
        return dao.findOtherPermissionById(id);
    }

    @Override
    public void addPermissionToRole(String roleId, String[] permissionIds) throws Exception {
        for (String permissionId : permissionIds) {
            dao.addPermissionToRole(roleId, permissionId);
        }
    }

}
