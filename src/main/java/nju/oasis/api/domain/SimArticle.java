package nju.oasis.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.List;

@Getter
@NoArgsConstructor
public class SimArticle {
    @Id
    private long articleId;
    @Field("name")
    private String name;
    @Field("authors")
    private List<SimAuthor>authors;
    @Field("publication")
    private Publication publication;
    @Field("abstract")
    private String abstractContent;
    @Field("hotLevel")
    private int hotLevel;
}
