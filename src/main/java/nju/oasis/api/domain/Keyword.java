package nju.oasis.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.List;

@Getter
@NoArgsConstructor
public class Keyword {

    @Id
    private int id;

    @Field("name")
    private String name;
}
