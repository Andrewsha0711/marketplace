package com.andrewsha.marketplace.domain.temp;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import lombok.Data;

@Document(indexName = "temp")
@Data
public class TempObject {
    @Id
    private String id;
   
    @Field(type = FieldType.Keyword)
    private String name;

    @Transient
    private String excluded;
}
