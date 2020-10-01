package com.ssm.service;

import com.ssm.domain.SysLog;

/**
 * @Author Harlan
 * @Date 2020/10/1
 */
public interface ISysLogService {

    /**
     * 保存日志
     * @param sysLog 日志信息
     * @throws Exception 异常
     */
    void save(SysLog sysLog) throws Exception;
}
