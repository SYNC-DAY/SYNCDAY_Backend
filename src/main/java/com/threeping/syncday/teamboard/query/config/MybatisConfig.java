package com.threeping.syncday.teamboard.query.config;

import com.threeping.syncday.teamboard.query.repository.TeamBoardMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan(basePackages = "com.threeping.syncday.teamboard.query.repository",annotationClass = Mapper.class)
@Configuration("TeamBoardMybatisConfiguration")
public class MybatisConfig {
    private TeamBoardMapper teamBoardMapper;
}
