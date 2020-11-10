package nju.oasis.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;

@Getter
@NoArgsConstructor
public class Direction {
    @Id
    private long directionId;
    @Field("name")
    private String name;
}
