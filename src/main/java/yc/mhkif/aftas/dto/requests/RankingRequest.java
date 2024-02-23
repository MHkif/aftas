package yc.mhkif.aftas.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import yc.mhkif.aftas.entities.Competition;
import yc.mhkif.aftas.entities.User;
import yc.mhkif.aftas.entities.implementations.CompetitionUser;

@Data
public class RankingRequest {

    @NotNull(message = "Competition & Member are Required")
    private CompetitionUser id;

    @NotNull(message = "Rank is Required")
    private int rank;

    @NotNull(message = "Score is Required")
    private int score;

    @NotNull(message = "Member is Required")
    private User user;

    @NotNull(message = "Competition is Required")
    private Competition competition;

}
