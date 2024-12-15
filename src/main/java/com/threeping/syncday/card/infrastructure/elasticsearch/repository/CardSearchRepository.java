package com.threeping.syncday.card.infrastructure.elasticsearch.repository;

import com.threeping.syncday.card.infrastructure.elasticsearch.document.CardDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardSearchRepository extends ElasticsearchRepository<CardDocument, Long> {
    @Query("{" +
            "  \"bool\": {" +
            "    \"should\": [" +
            "      {\"bool\": {" +
            "        \"should\": [" +
            "          {\"term\": {" +
            "            \"cardTitle\": {" +
            "              \"value\": \"?0\"," +
            "              \"boost\": 4.0" +
            "            }" +
            "          }}," +
            "          {\"term\": {" +
            "            \"cardTitle.ngram\": {" +
            "              \"value\": \"?0\"," +
            "              \"boost\": 3.5" +
            "            }" +
            "          }}" +
            "        ]" +
            "      }}," +
            "      {\"bool\": {" +
            "        \"should\": [" +
            "          {\"term\": {" +
            "            \"cardContent\": {" +
            "              \"value\": \"?0\"," +
            "              \"boost\": 3.0" +
            "            }" +
            "          }}," +
            "          {\"term\": {" +
            "            \"cardContent.ngram\": {" +
            "              \"value\": \"?0\"," +
            "              \"boost\": 2.5" +
            "            }" +
            "          }}" +
            "        ]" +
            "      }}," +
            "      {\"bool\": {" +
            "        \"should\": [" +
            "          {\"term\": {" +
            "            \"tags\": {" +
            "              \"value\": \"?0\"," +
            "              \"boost\": 2.0" +
            "            }" +
            "          }}," +
            "          {\"term\": {" +
            "            \"tags.ngram\": {" +
            "              \"value\": \"?0\"," +
            "              \"boost\": 1.5" +
            "            }" +
            "          }}" +
            "        ]" +
            "      }}," +
            "      {\"bool\": {" +
            "        \"should\": [" +
            "          {\"term\": {" +
            "            \"vcsObject\": {" +
            "              \"value\": \"?0\"," +
            "              \"boost\": 1.0" +
            "            }" +
            "          }}," +
            "          {\"term\": {" +
            "            \"vcsObject.ngram\": {" +
            "              \"value\": \"?0\"," +
            "              \"boost\": 0.5" +
            "            }" +
            "          }}" +
            "        ]" +
            "      }}" +
            "    ]," +
            "    \"minimum_should_match\": 1" +
            "  }" +
            "}")
    List<CardDocument> findByKeyword(String keyword);
}