package nju.oasis.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import nju.oasis.api.domain.ArticleES;
import nju.oasis.api.domain.Author;
import nju.oasis.api.domain.Keyword;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor

public class ArticleESVO implements Serializable {

    public ArticleESVO(ArticleES articleES){
        this.id = articleES.getId();
        this.name = articleES.getTitle();
        this.authors = new ArrayList<>();
        if(articleES.getAuthors()!=null&&articleES.getAuthors().size()>0) {
            for (Author author : articleES.getAuthors()) {
                this.authors.add(new AuthorVO((author)));
            }
        }
        this.publication = new PublicationVO(articleES.getPublication());
        this.abstractContent = articleES.getAbstractContent();
        this.referenceNum = articleES.getReferenceNum();
        this.citedNum = articleES.getCitedNum();
        this.citeds = articleES.getCiteds();
        this.source = articleES.getSource();
        this.date = articleES.getDate();
        this.directions = new ArrayList<>();
        for(Keyword keyword: articleES.getDirections()){
            this.directions.add(keyword.getName());
        }
        this.pdf_link = articleES.getPdf_link();
        this.theme = null;
        this.recommends = new ArrayList<>();
        this.hotLevel = articleES.getHotLevel();
    }

    private String id;

    private String name;

    private List<AuthorVO>authors;

    private PublicationVO publication;

    @JsonProperty("abstract")
    private String abstractContent;

    private int referenceNum;

    private List<Map<String, String>> references;

    private int citedNum;

    private List<String>citeds;

    private int source;

    private String date;

    private List<String>directions;

    private String pdf_link;

    private ThemeVO theme;

    private List<String> recommends;

    @JsonProperty("hot_level")
    private int hotLevel;
}
