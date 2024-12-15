package com.threeping.syncday.proj.infrastructure.elasticsearch.repository;

import com.threeping.syncday.proj.infrastructure.elasticsearch.document.ProjectSearchDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjSearchRepository extends ElasticsearchRepository<ProjectSearchDocument, Long> {
    @Query("{" +
            "  \"bool\": {" +
            "    \"should\": [" +
            "      {\"bool\": {" +
            "        \"should\": [" +
            "          {\"term\": {" +
            "            \"projectName\": {" +
            "              \"value\": \"?0\"," +
            "              \"boost\": 3.0" +
            "            }" +
            "          }}," +
            "          {\"term\": {" +
            "            \"projectName.ngram\": {" +
            "              \"value\": \"?0\"," +
            "              \"boost\": 2.0" +
            "            }" +
            "          }}" +
            "        ]" +
            "      }}," +
            "      {\"bool\": {" +
            "        \"should\": [" +
            "          {\"term\": {" +
            "            \"vcsType\": {" +
            "              \"value\": \"?0\"," +
            "              \"boost\": 1.5" +
            "            }" +
            "          }}," +
            "          {\"term\": {" +
            "            \"vcsType.ngram\": {" +
            "              \"value\": \"?0\"," +
            "              \"boost\": 1.0" +
            "            }" +
            "          }}" +
            "        ]" +
            "      }}" +
            "    ]," +
            "    \"minimum_should_match\": 1" +
            "  }" +
            "}")
    List<ProjectSearchDocument> searchByKeyword(String keyword);
}
