package com.ssm.dao;

import com.ssm.domain.Product;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/9/20
 */
@Repository
public interface IProductDao {

    /**
     * 查询所有产品信息
     * @return list
     * @throws Exception 异常
     */
    @Select("select * from product")
    List<Product> findAll() throws Exception;

    /**
     * 保存产品信息
     * @param product 产品信息
     */
    @Insert("insert into PRODUCT (productNum, productName, cityName, departureTime, productPrice, productDesc, productStatus)"+
            "values (#{productNum}, #{productName}, #{cityName}, #{departureTime}, #{productPrice}, #{productDesc}, #{productStatus})")
    void save(Product product);

    /**
     * 通过id查询产品
     * @param id 产品id
     * @return 产品信息
     * @throws Exception 异常
     */
    @Select("select * from product where id = #{id}")
    Product findById(String id) throws Exception;
}
