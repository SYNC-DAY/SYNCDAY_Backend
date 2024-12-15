package com.threeping.syncday.config;

import com.threeping.syncday.proj.command.aggregate.entity.Proj;
import com.threeping.syncday.projmember.command.aggregate.vo.UpdateProjRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfiguration {

    @Bean
    public ModelMapper modelMapper(){

        ModelMapper modelMapper = new ModelMapper();
        /* 설명. ModelMapper는 경우에 따라 자의적인 판단으로 필드끼리 매핑하는 경우가 있어 정확히 일치되게 매칭하려면 strict 설정해야됨 */
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);



        return modelMapper;
    }
}
