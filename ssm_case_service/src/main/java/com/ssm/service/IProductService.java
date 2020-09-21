package com.ssm.service;

import com.ssm.domain.Product;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/9/20
 */
public interface IProductService {

    /**
     * 查询所有产品
     * @return list
     * @throws Exception 异常
     */
    List<Product> findAll() throws Exception;
}