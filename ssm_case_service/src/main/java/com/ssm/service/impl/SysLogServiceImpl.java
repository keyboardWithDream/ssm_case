package com.ssm.service.impl;

import com.ssm.dao.ISysLogDao;
import com.ssm.domain.SysLog;
import com.ssm.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Harlan
 * @Date 2020/10/1
 */
@Service
@Transactional
public class SysLogServiceImpl implements ISysLogService {

    @Autowired
    private ISysLogDao dao;

    @Override
    public void save(SysLog sysLog) throws Exception {
        dao.save(sysLog);
    }
}
