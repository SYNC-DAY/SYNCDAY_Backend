package com.threeping.syncday.projmember.query.respository;

import com.threeping.syncday.projmember.query.aggregate.dto.ProjMemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjMemberMapper {
    List<ProjMemberDTO> selectProjMembersByUserId(Long userId);
}
