package com.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.ssm.dao.IPermissionDao;
import com.ssm.domain.Permission;
import com.ssm.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/9/27
 */
@Service
@Transactional
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private IPermissionDao dao;

    @Override
    public List<Permission> findAll(int page, int size) throws Exception {
        PageHelper.startPage(page, size);
        return dao.findAll();
    }

    @Override
    public void save(Permission permission) throws Exception {
        dao.save(permission);
    }

    @Override
    public Permission findById(String id) throws Exception {
        return dao.findById(id);
    }

    @Override
    public void deleteById(String id) throws Exception {
        dao.deletePermissionFromRolePermissionById(id);
        dao.deleteById(id);
    }
}
