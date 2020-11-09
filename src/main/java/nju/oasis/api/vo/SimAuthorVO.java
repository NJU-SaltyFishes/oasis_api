package nju.oasis.api.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import nju.oasis.api.domain.SimAuthor;

@Data
@NoArgsConstructor
public class SimAuthorVO {

    private long authorId;

    private String name;

    public SimAuthorVO(SimAuthor simAuthor){
        if(simAuthor!=null){
            this.authorId = simAuthor.getAuthorId();
            this.name = simAuthor.getName();
        }
    }
}
