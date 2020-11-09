package nju.oasis.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.List;

@Document(indexName = "author")
@Getter
@NoArgsConstructor
public class AuthorES {
    @Id
    private String id;
    @Field("authorId")
    private long authorId;
    @Field("name")
    private List<String> name;
    @Field("avatar")
    private String avatar;
    @Field("affiliation")
    private Affiliation affiliation;
    @Field("affiliationHistory")
    private List<Affiliation>affiliationHistory;
    @Field("direction")
    private Direction direction;
    @Field("directionHistory")
    private List<Direction>directionHistory;
    @Field("directionPredicted")
    private Direction directionPredicted;
    @Field("coAuthors")
    private List<CoAuthor>coAuthors;
    @Field("citedNum")
    private int citedNum;
    @Field("referenceNum")
    private int referenceNum;
    @Field("referenceDirection")
    private Direction referenceDirection;
    @Field("influence")
    private int influence;
    @Field("influenceLevel")
    private int influenceLevel;
    @Field("publicationYear")
    private List<Integer>publicationYear;
    @Field("articleYear")
    private List<YArticle>articleYear;
    @Field("authorArticle")
    private List<SimArticle>authorArticle;
}
