package com.andrewsha.marketplace.domain.temp;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import lombok.Data;

@Document(indexName = "temp")
@Data
public class TempObject2 {
    @Id
    private String id;
    @Field(type = FieldType.Keyword)
    private String name;
}
