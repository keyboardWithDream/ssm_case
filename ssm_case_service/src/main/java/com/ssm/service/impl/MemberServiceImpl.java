package com.ssm.service.impl;

import com.ssm.dao.IMemberDao;
import com.ssm.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Harlan
 * @Date 2020/9/24
 */
@Service
@Transactional
public class MemberServiceImpl implements IMemberService {

    @Autowired
    private IMemberDao dao;
}
