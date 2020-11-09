package nju.oasis.api.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import nju.oasis.api.domain.Author;

@Data
@NoArgsConstructor

public class AuthorVO {

    private long authorId;
    private String name;
    private String avatar;
    private String affiliationName;
    private long affiliationId;

    public AuthorVO(Author author){
        if(author!=null) {
            this.authorId = author.getAuthorId();
            this.name = author.getName();
            this.affiliationName = author.getAffiliationName();
            this.avatar = author.getAvatar();
            this.affiliationId = author.getAffiliationId();
        }
    }
}
