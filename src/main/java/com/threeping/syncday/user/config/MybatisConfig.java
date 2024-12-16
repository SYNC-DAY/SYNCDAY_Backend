package com.threeping.syncday.user.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan(basePackages = "com.threeping.syncday.user.query.repository", annotationClass = Mapper.class)
@Configuration("userMybatisConfig")
public class MybatisConfig {
//    private UserMapper userMapper;
}
