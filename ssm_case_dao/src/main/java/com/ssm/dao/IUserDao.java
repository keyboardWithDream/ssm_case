package com.ssm.dao;

import com.ssm.domain.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/9/25
 */
@Repository
public interface IUserDao {

    /**
     * 通过username查询user
     * @param username username
     * @return UserInfo
     * @throws Exception 异常
     */
    @Select("select * from users where username = #{username}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "status", column = "status"),
            @Result(property = "roles", column = "id", javaType = java.util.List.class, many = @Many(select = "com.ssm.dao.IRoleDao.findRoleByUserId"))
    })
    UserInfo findByUsername(String username) throws Exception;

    /**
     * 查询所有用户
     * @return 用户list
     */
    @Select({"select * from users"})
    List<UserInfo> findAll();

    /**
     * 通过id查询用户详情
     * @param id 用户id
     * @return 用户详情
     */
    @Select("select * from users where id = #{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "status", column = "status"),
            @Result(property = "roles", column = "id", javaType = java.util.List.class, many = @Many(select = "com.ssm.dao.IRoleDao.findRoleByUserId"))
    })
    UserInfo findById(String id);

    /**
     * 保存用户
     * @param userInfo 用户信息
     */
    @Insert("insert into users values(#{id}, #{email}, #{username}, #{password}, #{phoneNum}, #{status})")
    void save(UserInfo userInfo);

    /**
     * 通过角色id查询用户信息
     * @param id 角色id
     * @return 用户信息
     * @throws Exception 异常
     */
    @Select("select * from users where id in (select userId from users_role where roleId = #{id})")
    UserInfo findUserByRoleId(String id) throws Exception;
}
