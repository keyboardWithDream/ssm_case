package com.ssm.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码加密工具类
 * @Author Harlan
 * @Date 2020/9/27
 */
public class BCryptPasswordEncoderUtils {

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    private static String encoderPassword(String password){
        return PASSWORD_ENCODER.encode(password);
    }
}
