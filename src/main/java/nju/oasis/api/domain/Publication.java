package nju.oasis.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "article")
@Getter
@NoArgsConstructor
public class Publication {
    @Id
    private long publicationId;
    @Field("name")
    private String name;
}
