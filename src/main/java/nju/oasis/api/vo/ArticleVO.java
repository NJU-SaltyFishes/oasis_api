package nju.oasis.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import nju.oasis.api.domain.Article;
import nju.oasis.api.domain.Author;
import nju.oasis.api.domain.Keyword;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ArticleVO {

    public ArticleVO(Article article){
        this.id = article.getId();
        this.name = article.getTitle();
        this.authors = new ArrayList<>();
        for(Author author:article.getAuthors()){
            this.authors.add(new AuthorVO((author)));
        }
        this.publication = new PublicationVO(article.getPublication());
        this.abstractContent = article.getAbstractContent();
        this.referenceNum = article.getReferenceNum();
        this.references = article.getReferences();
        this.citedNum = article.getCitedNum();
        this.citeds = article.getCiteds();
        this.source = article.getSource();
        this.date = article.getDate();
        this.directions = new ArrayList<>();
        for(Keyword keyword:article.getDirections()){
            this.directions.add(keyword.getName());
        }
        this.pdf_link = article.getPdf_link();
        this.theme = null;
        this.recommends = new ArrayList<>();
        this.hotLevel = article.getHotLevel();
    }

    private String id;

    private String name;

    private List<AuthorVO>authors;

    private PublicationVO publication;

    @JsonProperty("abstract")
    private String abstractContent;

    private int referenceNum;

    private List<String>references;

    private int citedNum;

    private List<String>citeds;

    private int source;

    private String date;

    private List<String>directions;

    private String pdf_link;

    private ThemeVO theme;

    private List<String> recommends;

    private int hotLevel;
}
