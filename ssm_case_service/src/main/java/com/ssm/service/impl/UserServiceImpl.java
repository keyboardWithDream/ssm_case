package com.ssm.service.impl;

import com.ssm.dao.IUserDao;
import com.ssm.domain.Role;
import com.ssm.domain.UserInfo;
import com.ssm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author Harlan
 * @Date 2020/9/25
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao dao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserInfo userInfo = null;
        try {
            userInfo = dao.findByUsername(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert userInfo != null;
        //处理自己的用户对象封装成UserDetails
        return new User(userInfo.getUsername(), userInfo.getPassword(), userInfo.getStatus() == 1, true, true, true, getAuthority(userInfo.getRoles()));
    }

    /**
     * 返回一个list集合, 集合中装入的是角色描述
     * @return list集合
     */
    private List<SimpleGrantedAuthority> getAuthority(List<Role> roles){
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        }
        return list;
    }

    @Override
    public List<UserInfo> findAll() throws Exception {
        return dao.findAll();
    }

    @Override
    public UserInfo findById(String id) throws Exception {
        return dao.findById(id);
    }

    @Override
    public void save(UserInfo userInfo) throws Exception {
        userInfo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        userInfo.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
        System.out.println(userInfo);
        dao.save(userInfo);
    }

}
