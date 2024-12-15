package com.threeping.syncday.teamcomment.query.config;

import com.threeping.syncday.teamcomment.query.repository.TeamCommentMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration(value = "TeamCommentMapperConfiguration")
@MapperScan(basePackages = "com.threeping.syncday.teamcomment.query.repository",annotationClass = Mapper.class)
public class MybatisConfig {
    private TeamCommentMapper teamCommentMapper;
}
