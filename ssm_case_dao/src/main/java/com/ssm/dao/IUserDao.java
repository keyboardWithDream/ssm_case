package com.ssm.dao;

import com.ssm.domain.UserInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

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
    UserInfo findByUsername(String username) throws Exception;
}
