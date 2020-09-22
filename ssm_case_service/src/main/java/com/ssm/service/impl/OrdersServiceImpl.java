package com.ssm.service.impl;

import com.ssm.dao.IOrdersDao;
import com.ssm.domain.Orders;
import com.ssm.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public List<Orders> findAll() throws Exception {
        return dao.findAll();
    }

}
