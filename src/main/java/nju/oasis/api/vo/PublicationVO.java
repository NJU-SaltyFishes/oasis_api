package nju.oasis.api.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import nju.oasis.api.domain.Publication;

@Data
@NoArgsConstructor
public class PublicationVO {

    private long publicationId;
    private String name;

    public PublicationVO(Publication publication){
        this.publicationId = publication.getPublicationId();
        this.name = publication.getName();
    }
}
