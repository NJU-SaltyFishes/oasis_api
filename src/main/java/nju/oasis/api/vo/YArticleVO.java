package nju.oasis.api.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import nju.oasis.api.domain.YArticle;

@Data
@NoArgsConstructor
public class YArticleVO {

    private int year;

    private int amount;

    public YArticleVO(YArticle yArticle){
        if(yArticle!=null){
            this.year = yArticle.getYear();
            this.amount = yArticle.getAmount();
        }
    }
}
