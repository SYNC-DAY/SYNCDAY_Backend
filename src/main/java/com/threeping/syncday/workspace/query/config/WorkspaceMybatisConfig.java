package com.threeping.syncday.workspace.query.config;

import com.threeping.syncday.workspace.query.repository.WorkspaceMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.threeping.syncday.workspace.query.repository", annotationClass = Mapper.class)
public class WorkspaceMybatisConfig {
    private WorkspaceMapper workspaceMapper;
}
