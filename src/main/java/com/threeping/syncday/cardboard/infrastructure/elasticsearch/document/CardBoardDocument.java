package com.threeping.syncday.cardboard.infrastructure.elasticsearch.document;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;

@Document(indexName = "cardboard_search")
@Getter
@Builder
public class CardBoardDocument {
    @Id
    private Long cardboardId;
    @MultiField(
            mainField = @Field(type = FieldType.Text),
            otherFields = {
                    @InnerField(suffix = "ngram", type = FieldType.Text, analyzer = "ngram_analyzer")
            }
    )
    private String cardboardName;
    @Field(type = FieldType.Keyword)
    private Long projectId;
    @Field(type = FieldType.Keyword)
    private Long workspaceId;
    @Field(type = FieldType.Keyword)
    private String workspaceName;
    @MultiField(
            mainField = @Field(type = FieldType.Text),
            otherFields = {
                    @InnerField(suffix = "ngram", type = FieldType.Text, analyzer = "ngram_analyzer")
            }
    )
    private String vcsType;
    @Field(type = FieldType.Keyword)
    private String vcsMilestoneUrl;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime createdAt;
}
