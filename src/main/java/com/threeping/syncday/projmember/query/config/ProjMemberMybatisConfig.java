package com.threeping.syncday.projmember.query.config;

import com.threeping.syncday.projmember.query.respository.ProjMemberMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.threeping.syncday.projmember.query.respository", annotationClass = Mapper.class)
public class ProjMemberMybatisConfig {

    private ProjMemberMapper projMemberMapper;
}
