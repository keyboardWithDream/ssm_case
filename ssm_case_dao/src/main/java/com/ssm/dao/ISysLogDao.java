package com.ssm.dao;

import com.ssm.domain.SysLog;
import org.apache.ibatis.annotations.Insert;

/**
 * @Author Harlan
 * @Date 2020/10/1
 */
public interface ISysLogDao {

    /**
     * 保存日志信息
     * @param sysLog 日志信息
     * @throws Exception 异常
     */
    @Insert("insert into syslog(visitTime, username, ip, url, executionTime, method) values(#{visitTime}, #{username}, #{ip}, #{url}, #{executionTime}, #{method})")
    void save(SysLog sysLog) throws Exception;
}
