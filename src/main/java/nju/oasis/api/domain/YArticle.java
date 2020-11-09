package nju.oasis.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

@Getter
@NoArgsConstructor
public class YArticle {

    @Field("year")
    private int year;

    @Field("amount")
    private int amount;
}
