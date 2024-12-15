package com.threeping.syncday.user.infrastructure.elasticsearch.document;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Document(indexName = "user_search")
@Getter
@Builder
public class UserSearchDocument {
    @Id
    private Long userId;
    @MultiField(
            mainField = @Field(type = FieldType.Text),  // 기본 text 타입
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)  // keyword 타입 추가
            }
    )
    private String name;
    @Field(type = FieldType.Keyword)
    private String email;
    @Field(type = FieldType.Keyword)
    private String profileImage;
    @Field(type = FieldType.Text, analyzer = "nori_mixed")
    private String teamName;
    @Field(type = FieldType.Keyword)
    private String position;
}
