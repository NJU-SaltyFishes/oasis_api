package nju.oasis.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import nju.oasis.api.config.Model;
import nju.oasis.api.domain.SimArticle;
import nju.oasis.api.domain.SimAuthor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SimArticleVO {

    private long articleId;

    private String name;

    private List<SimAuthorVO>authors;

    private PublicationVO publication;

    @JsonProperty("abstract")
    private String abstractContent;

    private int hotLevel;

    public SimArticleVO(SimArticle simArticle){
        if(simArticle!=null){
            this.articleId = simArticle.getArticleId();
            this.name = simArticle.getName();
            this.authors = new ArrayList<>();
            for(SimAuthor simAuthor:simArticle.getAuthors()){
                SimAuthorVO simAuthorVO = new SimAuthorVO(simAuthor);
                this.authors.add(simAuthorVO);
            }
            this.publication = new PublicationVO(simArticle.getPublication());
            this.abstractContent = simArticle.getAbstractContent();
            if(this.abstractContent.length()> Model.MAX_ABSTRACT_LENGTH){
                this.abstractContent = this.abstractContent.substring(0,Model.MAX_ABSTRACT_LENGTH);
            }
            int index = this.abstractContent.lastIndexOf(".");
            if (index > 0) {
                this.abstractContent = this.abstractContent.substring(0,index+1);
            }
            this.hotLevel = simArticle.getHotLevel();
        }
    }
}
