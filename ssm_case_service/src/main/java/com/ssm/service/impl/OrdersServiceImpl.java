package com.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.ssm.dao.IOrdersDao;
import com.ssm.domain.Orders;
import com.ssm.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/9/22
 */
@Service
@Transactional
public class OrdersServiceImpl implements IOrdersService {

    @Autowired
    private IOrdersDao dao;

    @Override
    public List<Orders> findAll(int page, int size) throws Exception {
        //分页查询 pageNum: 页码值, pageSize: 每页条数
        PageHelper.startPage(page, size);
        return dao.findAll();
    }

    @Override
    public Orders findById(String id) throws Exception {
        return dao.findById(id);
    }

}
