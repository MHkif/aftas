package yc.mhkif.aftas.entities;

import jakarta.persistence.*;
import lombok.Data;
import yc.mhkif.aftas.entities.implementations.CompetitionUser;

@Data
@Entity
public class Ranking {

    @EmbeddedId
    private CompetitionUser id;

    private int rank;
    private int score;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_num")
    private User user;
    @ManyToOne
    @MapsId("competitionCode")
    @JoinColumn(name = "competition_code")
    private Competition competition;

}
