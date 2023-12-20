package yc.mhkif.aftas.entities;

import jakarta.persistence.*;
import lombok.Data;
import yc.mhkif.aftas.entities.implementations.CompetitionMember;

@Data
@Entity
public class Ranking {

    @EmbeddedId
    private CompetitionMember id;

    private int rank;
    private int score;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_num")
    private Member member;
    @ManyToOne
    @MapsId("competitionCode")
    @JoinColumn(name = "competition_code")
    private Competition competition;

}
