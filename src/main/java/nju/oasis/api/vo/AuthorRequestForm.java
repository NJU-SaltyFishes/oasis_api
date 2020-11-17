package nju.oasis.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class AuthorRequestForm {
    /**
     * author的id
     */
    private Long authorId;

    /**
     * author所有文章的id
     */
    private List<Long> articleIds;

}
