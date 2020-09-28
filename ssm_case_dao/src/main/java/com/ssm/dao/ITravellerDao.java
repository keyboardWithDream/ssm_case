package com.ssm.dao;

import com.ssm.domain.Traveller;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/9/24
 */
@Repository
public interface ITravellerDao {

    /**
     * 通过id查询旅客信息
     * @param id 旅客id
     * @return 旅客谢谢
     * @throws Exception 异常
     */
    @Select("select * from traveller where id = #{id}")
    Traveller findById(String id) throws Exception;

    /**
     * 通过订单id查询旅客
     * @param id 订单id
     * @return 旅客集合
     * @throws Exception 异常
     */
    @Select("select * from traveller where id in (select travellerId from order_traveller where orderId = #{ordersId})")
    List<Traveller> findByOrdersId(String id) throws Exception;
}
