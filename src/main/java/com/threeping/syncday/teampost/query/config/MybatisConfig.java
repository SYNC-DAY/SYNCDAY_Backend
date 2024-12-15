package com.threeping.syncday.teampost.query.config;

import com.threeping.syncday.teampost.query.repository.TeamPostMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration(value = "TeamPostMapperConfiguration")
@MapperScan(basePackages = "com.threeping.syncday.teampost.query.repository",annotationClass = Mapper.class)
public class MybatisConfig {
    private TeamPostMapper teamPostMapper;
}