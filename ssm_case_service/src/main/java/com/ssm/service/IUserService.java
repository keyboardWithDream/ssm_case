package com.ssm.service;

import com.ssm.domain.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/9/25
 */
@Service
@Transactional
public interface IUserService extends UserDetailsService {

    /**
     * 查询所有用户
     * @param page 当前页码
     * @param size 每页显示数量
     * @return 用户list
     * @throws Exception 异常
     */
    List<UserInfo> findAll(int page, int size) throws Exception;

    /**
     * 通过id查询用户
     * @param id 用户id
     * @return 用户信息
     * @throws Exception 异常
     */
    UserInfo findById(String id) throws Exception;

    /**
     * 保存用户信息
     * @param userInfo 用户信息
     * @throws Exception 异常
     */
    void save(UserInfo userInfo) throws Exception;
}
