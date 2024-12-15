package com.threeping.syncday.card.infrastructure.elasticsearch.document;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import java.time.LocalDateTime;

@Document(indexName = "card_search")
@Getter
@Builder
@ToString
public class CardDocument {
    @Id
    private Long cardId;
    @MultiField(
            mainField = @Field(type = FieldType.Text),
            otherFields = {
                    @InnerField(suffix = "ngram", type = FieldType.Text, analyzer = "ngram_analyzer")
            }
    )
    private String cardTitle;
    @MultiField(
            mainField = @Field(type = FieldType.Text),
            otherFields = {
                    @InnerField(suffix = "ngram", type = FieldType.Text, analyzer = "ngram_analyzer")
            }
    )
    private String cardContent;
    @Field(type = FieldType.Keyword)
    private Long cardboardId;
    @Field(type = FieldType.Keyword)
    private String cardboardName;
    @Field(type = FieldType.Keyword)
    private Long workspaceId;
    @Field(type = FieldType.Keyword)
    private String workspaceName;
    @Field(type = FieldType.Keyword)
    private Long projectId;
    @Field(type = FieldType.Keyword)
    private Long creatorId;
    @Field(type = FieldType.Keyword)
    private String creatorName;
    @Field(type = FieldType.Keyword)
    private Long assigneeId;
    @Field(type = FieldType.Keyword)
    private String assigneeName;
    @MultiField(
            mainField = @Field(type = FieldType.Text),
            otherFields = {
                    @InnerField(suffix = "ngram", type = FieldType.Text, analyzer = "ngram_analyzer")
            }
    )
    private String tags;
    @MultiField(
            mainField = @Field(type = FieldType.Text),
            otherFields = {
                    @InnerField(suffix = "ngram", type = FieldType.Text, analyzer = "ngram_analyzer")
            }
    )
    private String vcsObject;
    @Field(type = FieldType.Keyword)
    private String vcsUrl;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime createdAt;
}