package com.ssm.service;

import com.ssm.domain.Orders;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/9/22
 */
public interface IOrdersService {

    /**
     * 查询订单
     * @param page 页码
     * @param size 条数
     * @return List
     * @throws Exception 异常
     */
    List<Orders> findAll(int page, int size) throws Exception;


    /**
     * 通过id查询订单
     * @param id 订单id
     * @return 订单信息
     * @throws Exception 异常
     */
    Orders findById(String id) throws Exception;
}
