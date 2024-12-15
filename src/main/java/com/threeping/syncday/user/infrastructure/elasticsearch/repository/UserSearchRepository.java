package com.threeping.syncday.user.infrastructure.elasticsearch.repository;

import com.threeping.syncday.user.command.domain.aggregate.UserEntity;
import com.threeping.syncday.user.infrastructure.elasticsearch.document.UserSearchDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSearchRepository extends ElasticsearchRepository<UserSearchDocument, Long> {
    @Query("{"
            + "\"bool\": {"
            + "  \"should\": ["
            + "    {\"wildcard\": {"
            + "      \"name.keyword\": \"*?0*\""  // name은 keyword 필드로 wildcard 검색
            + "    }},"
            + "    {\"match\": {"
            + "      \"teamName\": \"?0\""        // teamName은 nori_mixed로 분석된 필드 검색
            + "    }}"
            + "  ]"
            + "}}")
    List<UserSearchDocument> searchByKeyword(String keyword);

}
