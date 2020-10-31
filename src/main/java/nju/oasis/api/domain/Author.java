package nju.oasis.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.List;

@Getter
@NoArgsConstructor
public class Author {
    @Id
    private long authorId;
    @Field("name")
    private String name;
    @Field("avatar")
    private String avatar;
    @Field("affiliationName")
    private String affiliationName;
    @Field("affiliationId")
    private long affiliationId;

}
