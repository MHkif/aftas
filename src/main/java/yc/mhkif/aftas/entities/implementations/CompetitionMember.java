package yc.mhkif.aftas.entities.implementations;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yc.mhkif.aftas.entities.Competition;
import yc.mhkif.aftas.entities.Member;

import java.io.Serializable;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionMember implements Serializable {

    @Column(name = "member_num")
    private Integer memberId;
    @Column(name = "competition_code")
    private String competitionCode;


}
