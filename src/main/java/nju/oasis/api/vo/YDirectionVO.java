package nju.oasis.api.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class YDirectionVO {

    private int year;

    private List<DirectionVO>directions;

    public YDirectionVO(int year,List<DirectionVO>directions){
        this.year = year;
        this.directions = directions;
    }
}
