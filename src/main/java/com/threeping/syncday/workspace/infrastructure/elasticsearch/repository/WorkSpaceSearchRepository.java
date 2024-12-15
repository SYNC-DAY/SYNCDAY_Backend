package com.threeping.syncday.workspace.infrastructure.elasticsearch.repository;

import com.threeping.syncday.workspace.infrastructure.elasticsearch.document.WorkSpaceDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkSpaceSearchRepository extends ElasticsearchRepository<WorkSpaceDocument, Long> {
    @Query("{" +
            "  \"bool\": {" +
            "    \"should\": [" +
            "      {\"bool\": {" +
            "        \"should\": [" +
            "          {\"term\": {" +
            "            \"workspaceName\": {" +
            "              \"value\": \"?0\"," +
            "              \"boost\": 3.5" +
            "            }" +
            "          }}," +
            "          {\"term\": {" +
            "            \"workspaceName.ngram\": {" +
            "              \"value\": \"?0\"," +
            "              \"boost\": 3.0" +
            "            }" +
            "          }}" +
            "        ]" +
            "      }}," +
            "      {\"bool\": {" +
            "        \"should\": [" +
            "          {\"term\": {" +
            "            \"vcsRepoName\": {" +
            "              \"value\": \"?0\"," +
            "              \"boost\": 2.5" +
            "            }" +
            "          }}," +
            "          {\"term\": {" +
            "            \"vcsRepoName.ngram\": {" +
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
    List<WorkSpaceDocument> searchByKeyword(String keyword);
}
