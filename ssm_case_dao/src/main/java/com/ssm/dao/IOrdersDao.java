package com.ssm.dao;

import com.ssm.domain.Member;
import com.ssm.domain.Orders;
import com.ssm.domain.Product;
import com.ssm.domain.Traveller;
import org.apache.ibatis.annotations.*;
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
            @Result(property = "product", column = "productId", javaType = Product.class, one = @One(select = "com.ssm.dao.IProductDao.findById"))
    })
    List<Orders> findAll() throws Exception;


    /**
     * 通过id查询订单
     * @param id 订单id
     * @return 订单信息
     * @throws Exception 异常
     */
    @Select("select * from orders where id = #{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "orderNum", column = "orderNum"),
            @Result(property = "orderTime", column = "orderTime"),
            @Result(property = "peopleCount", column = "peopleCount"),
            @Result(property = "orderDesc", column = "orderDesc"),
            @Result(property = "payType", column = "payType"),
            @Result(property = "orderStatus", column = "orderStatus"),
            @Result(property = "member", column = "memberId", javaType = Member.class, one = @One(select = "com.ssm.dao.IMemberDao.findById")),
            @Result(property = "travellers", column = "id", javaType = java.util.List.class, many = @Many(select = "com.ssm.dao.ITravellerDao.findByOrdersId")),
            @Result(property = "product", column = "productId", javaType = Product.class, one = @One(select = "com.ssm.dao.IProductDao.findById"))
    })
    Orders findById(String id) throws Exception;
}
