package nju.oasis.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;

@Getter
@NoArgsConstructor
public class SimAuthor {
    @Id
    private long authorId;
    @Field("name")
    private String name;
}
