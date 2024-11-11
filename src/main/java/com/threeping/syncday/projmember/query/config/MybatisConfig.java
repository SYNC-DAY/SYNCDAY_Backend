package com.threeping.syncday.projmember.query.config;


import com.threeping.syncday.projmember.query.repository.ProjMemberMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan(basePackages = "com.threeping.syncday.projmember.query.repository", annotationClass = Mapper.class)
@Configuration("ProjMemberMybaitsConfiguration")
public class MybatisConfig {
    private ProjMemberMapper projMemberMapper;
}
