package com.ssm.dao;

import com.ssm.domain.Product;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/9/20
 */
public interface IProductDao {

    /**
     * 查询所有产品信息
     * @return list
     * @throws Exception 异常
     */
    @Select("select * from product")
    List<Product> findAll() throws Exception;
}
