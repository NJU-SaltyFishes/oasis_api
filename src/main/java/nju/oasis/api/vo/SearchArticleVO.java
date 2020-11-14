package nju.oasis.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import nju.oasis.api.config.Model;
import nju.oasis.api.domain.ArticleES;
import nju.oasis.api.domain.Author;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SearchArticleVO implements Serializable {
    public SearchArticleVO(ArticleES article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.authors = new ArrayList<>();
        for(Author author:article.getAuthors()){
            this.authors.add(new AuthorVO((author)));
        }
        this.publication = new PublicationVO(article.getPublication());
        this.abstractContent = article.getAbstractContent();
        this.date = article.getDate();
        this.source = (article.getSource() == 2 ? Model.ACM : Model.IEEE);
        this.hotLevel = article.getHotLevel();
    }

    private String id;

    private String title;

    private List<AuthorVO> authors;

    private PublicationVO publication;

    @JsonProperty("abstract")
    private String abstractContent;

    private String date;

    private String source;

    @JsonProperty("hot_level")
    private int hotLevel;
}
