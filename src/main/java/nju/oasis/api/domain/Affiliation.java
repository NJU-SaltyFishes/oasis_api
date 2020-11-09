package nju.oasis.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;

@Getter
@NoArgsConstructor
public class Affiliation {
    @Id
    private long affiliationId;
    @Field("name")
    private String name;
}
