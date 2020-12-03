package nju.oasis.api.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class AffiliationInfoVO {

    private long affiliationId;

    private String name;

    private double averageCitation;

    private int citationNum;

    private int publicationNum;

    private int startYear;

    private int endYear;

    private int availableDownload;

    private double averageDownload;

    private SimArticleVO newestArticle;

    private Map<String,Object>topCitedAuthor;

    private Map<String,Object>topDirection;

    private List<Map<String,Object>>directions;

    private Map<String,Object>topCoAffiliation;

    private List<Map<String,Object>>collaborationAffiliation;

    private List<Map<String,Object>>publicationYear;
}
