package com.ssm.dao;

import com.ssm.domain.Orders;
import com.ssm.domain.Product;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/9/22
 */
@Repository
public interface IOrdersDao {

    /**
     * 查询所有订单
     * @return list
     * @throws Exception 异常
     */
    @Select("select * from orders")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "orderNum", column = "orderNum"),
            @Result(property = "orderTime", column = "orderTime"),
            @Result(property = "peopleCount", column = "peopleCount"),
            @Result(property = "orderDesc", column = "orderDesc"),
            @Result(property = "payType", column = "payType"),
            @Result(property = "orderStatus", column = "orderStatus"),
//            @Result(property = "member", column = "memberId"),
//            @Result(property = "traveller", column = "traveller"),
            @Result(property = "product", column = "productId", javaType = Product.class, one = @One(select = "com.ssm.dao.IProductDao.findById"))
    })
    List<Orders> findAll() throws Exception;
}
