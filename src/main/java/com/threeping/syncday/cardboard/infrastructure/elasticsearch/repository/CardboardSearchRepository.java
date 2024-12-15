package com.threeping.syncday.cardboard.infrastructure.elasticsearch.repository;

import com.threeping.syncday.cardboard.infrastructure.elasticsearch.document.CardBoardDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardboardSearchRepository extends ElasticsearchRepository<CardBoardDocument, Long> {
    @Query("{" +
            "  \"bool\": {" +
            "    \"should\": [" +
            "      {\"bool\": {" +
            "        \"should\": [" +
            "          {\"term\": {" +
            "            \"cardboardName\": {" +
            "              \"value\": \"?0\"," +
            "              \"boost\": 3.0" +
            "            }" +
            "          }}," +
            "          {\"term\": {" +
            "            \"cardboardName.ngram\": {" +
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
    List<CardBoardDocument> searchByKeyword(String keyword);
}
