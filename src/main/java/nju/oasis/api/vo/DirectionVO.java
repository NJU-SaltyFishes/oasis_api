package nju.oasis.api.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import nju.oasis.api.domain.Direction;

@Data
@NoArgsConstructor
public class DirectionVO {

    private long directionId;

    private String name;

    public DirectionVO(Direction direction){
        if(direction !=null) {
            this.directionId = direction.getDirectionId();
            this.name = direction.getName();
        }
    }

    public DirectionVO(String name){
        this.name = name;
    }
}
