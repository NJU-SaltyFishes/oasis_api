package nju.oasis.api.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import nju.oasis.api.domain.CoAuthor;

@Data
@NoArgsConstructor
public class CoAuthorVO {

    private long authorId;

    private String name;

    private DirectionVO coDirection;

    public CoAuthorVO(CoAuthor coAuthor){
        if(coAuthor!=null){
            this.authorId = coAuthor.getAuthorId();
            this.name = coAuthor.getName();
            this.coDirection = new DirectionVO(coAuthor.getCoDirection());
        }
    }
}
