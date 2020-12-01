package nju.oasis.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import nju.oasis.api.domain.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class AuthorESVO {

    private long authorId;

    private List<String>name;

    private String avatar;

    private AffiliationVO affiliation;

    private List<String>affiliationHistory;

    private DirectionVO direction;

    private List<DirectionVO>directionHistory;

    private DirectionVO directionPredicted;

    private List<CoAuthorVO>coAuthors;

    private int citedNum;

    private int referenceNum;

    private DirectionVO referenceDirection;

    private int influence;

    private int influenceLevel;

    private List<Integer>publicationYear;

    private List<YArticleVO>articleYear;

    private List<SimArticleVO>authorArticle;

    private CoAuthorVO mostFrequentCoAuthor;

    private Map<String, Object> mostCitedArticle;

    private List<YDirectionVO>directionYear;

    public AuthorESVO(AuthorES authorES){
        this.authorId = authorES.getAuthorId();
        this.name = authorES.getName();
        this.avatar = authorES.getAvatar();

        if(authorES.getAffiliation()!=null) {
            this.affiliation = new AffiliationVO(authorES.getAffiliation());
        }
        this.affiliationHistory = new ArrayList<>();
        if(authorES.getDirectionHistory()!=null&&authorES.getDirectionHistory().size()>0) {
            for (Affiliation affiliation : authorES.getAffiliationHistory()) {
                AffiliationVO affiliationVO = new AffiliationVO(affiliation);
                this.affiliationHistory.add(affiliationVO.getName());
            }
        }
        if(authorES.getDirection()!=null) {
            this.direction = new DirectionVO(authorES.getDirection());
        }
        this.directionHistory = new ArrayList<>();
        if(authorES.getDirectionHistory()!=null&&authorES.getDirectionHistory().size()>0) {
            for (Direction direction : authorES.getDirectionHistory()) {
                DirectionVO directionVO = new DirectionVO(direction);
                this.directionHistory.add(directionVO);
            }
        }
        this.directionPredicted = null;
        this.coAuthors = new ArrayList<>();
        if(authorES.getCoAuthors()!=null&&authorES.getCoAuthors().size()>0) {
            for (CoAuthor coAuthor : authorES.getCoAuthors()) {
                CoAuthorVO coAuthorVO = new CoAuthorVO(coAuthor);
                this.coAuthors.add(coAuthorVO);
            }
        }
        this.citedNum = authorES.getCitedNum();
        this.referenceNum = authorES.getReferenceNum();
        this.referenceDirection = null;
        this.influence = authorES.getInfluence();
        this.influenceLevel = authorES.getInfluenceLevel();
        this.publicationYear = authorES.getPublicationYear();
        this.articleYear = new ArrayList<>();
        if(authorES.getArticleYear()!=null&&authorES.getArticleYear().size()>0) {
            for (YArticle yArticle : authorES.getArticleYear()) {
                YArticleVO yArticleVO = new YArticleVO(yArticle);
                this.articleYear.add(yArticleVO);
            }
        }
        this.authorArticle = new ArrayList<>();
        if(authorES.getAuthorArticle()!=null&&authorES.getAuthorArticle().size()>0) {
            for (SimArticle simArticle : authorES.getAuthorArticle()) {
                SimArticleVO simArticleVO = new SimArticleVO(simArticle);
                this.authorArticle.add(simArticleVO);
            }
        }
    }

    public void setMostFrequentCoauthor(long mostFrequentCoauthorId){
        for(CoAuthorVO coAuthorVO:coAuthors){
            if(coAuthorVO.getAuthorId()==mostFrequentCoauthorId){
                this.mostFrequentCoAuthor = coAuthorVO;
                break;
            }
        }
    }

    public void setMostCitedArticle(long mostCitedArticleId,String mostCitedArticleName){
        this.mostCitedArticle = new HashMap<>();
        this.mostCitedArticle.put("articleId", mostCitedArticleId);
        this.mostCitedArticle.put("articleName",mostCitedArticleName);
    }
}
