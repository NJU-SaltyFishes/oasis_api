package nju.oasis.api.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import nju.oasis.api.domain.Affiliation;

@Data
@NoArgsConstructor
public class AffiliationVO {

    private long affiliationId;

    private String name;

    public AffiliationVO(Affiliation affiliation){
        if(affiliation!=null) {
            this.affiliationId = affiliation.getAffiliationId();
            this.name = affiliation.getName();
        }
    }

}
