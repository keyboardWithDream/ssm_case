package com.ssm.service;

import com.ssm.domain.Orders;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/9/22
 */
public interface IOrdersService {

    /**
     * 查询所有订单
     * @return list
     */
    List<Orders> findAll() throws Exception;
}
