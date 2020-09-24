package com.ssm.dao;

import com.ssm.domain.Member;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Author Harlan
 * @Date 2020/9/24
 */
@Repository
public interface IMemberDao {

    /**
     * 通过id查询会员
     * @param id 会员id
     * @return 会员信息
     * @throws Exception 异常
     */
    @Select("select * from member where id = #{id}")
    Member findById(String id) throws Exception;
}
