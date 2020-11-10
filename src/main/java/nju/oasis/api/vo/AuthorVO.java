package nju.oasis.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import nju.oasis.api.domain.Author;

import java.io.Serializable;

@Data
@NoArgsConstructor

public class AuthorVO implements Serializable {

    private long authorId;
    private String name;
    private String avatar;
    private String affiliationName;
    private long affiliationId;

    public AuthorVO(Author author){
        this.authorId = author.getAuthorId();
        this.name = author.getName();
        this.affiliationName = author.getAffiliationName();
        this.avatar = author.getAvatar();
        this.affiliationId = author.getAffiliationId();
    }
}
