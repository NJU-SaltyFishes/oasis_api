package nju.oasis.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import nju.oasis.api.domain.Author;
import nju.oasis.api.domain.AuthorES;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class SearchAuthorVO implements Serializable {
    @JsonProperty("id")
    private long authorId;
    private String name;
    private String avatar;


    public SearchAuthorVO(AuthorES author){
        if(author!=null) {
            this.authorId = author.getAuthorId();
            this.name = author.getName().get(0);
            this.avatar = author.getAvatar();
        }
    }
}