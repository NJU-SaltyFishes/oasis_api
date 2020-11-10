package nju.oasis.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import nju.oasis.api.domain.Article;
import nju.oasis.api.domain.Author;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SearchArticleVO implements Serializable {
    public SearchArticleVO(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.authors = new ArrayList<>();
        for(Author author:article.getAuthors()){
            this.authors.add(new AuthorVO((author)));
        }
        this.publication = new PublicationVO(article.getPublication());
        this.abstractContent = article.getAbstractContent();
        this.date = article.getDate();
        this.hotLevel = article.getHotLevel();
    }

    private String id;

    private String title;

    private List<AuthorVO> authors;

    private PublicationVO publication;

    @JsonProperty("abstract")
    private String abstractContent;

    private String date;

    @JsonProperty("hot_level")
    private int hotLevel;
}
