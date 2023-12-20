package yc.mhkif.aftas.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import yc.mhkif.aftas.entities.Competition;
import yc.mhkif.aftas.entities.Member;
import yc.mhkif.aftas.entities.implementations.CompetitionMember;

@Data
public class RankingRequest {

    @NotNull(message = "Competition & Member are Required")
    private CompetitionMember id;

    @NotNull(message = "Rank is Required")
    private int rank;

    @NotNull(message = "Score is Required")
    private int score;

    @NotNull(message = "Member is Required")
    private Member member;

    @NotNull(message = "Competition is Required")
    private Competition competition;

}
