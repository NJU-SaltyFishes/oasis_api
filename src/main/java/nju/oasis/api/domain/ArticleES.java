package nju.oasis.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.List;

@Document(indexName = "article")
@Getter
@NoArgsConstructor
public class ArticleES {
    @Id
    private String id;
    @Field("name")
    private String title;
    @Field("authors")
    private List<Author>authors;
    @Field("publication")
    private Publication publication;
    @Field("abstract")
    private String abstractContent;
    @Field("referenceNum")
    private int referenceNum;
    @Field("references")
    private List<String>references;
    @Field("citedNum")
    private int citedNum;
    @Field("citeds")
    private List<String>citeds;
    @Field("source")
    private int source;
    @Field("date")
    private String date;
    @Field("directions")
    private List<Keyword>directions;
    @Field("pdf_link")
    private String pdf_link;
    @Field("theme")
    private String theme;
    @Field("recommends")
    private List<String>recommends;
    @Field("hotLevel")
    private int hotLevel;

}
