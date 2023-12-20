package yc.mhkif.aftas.dtos.responses;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import yc.mhkif.aftas.entities.Competition;
import yc.mhkif.aftas.entities.Member;
import yc.mhkif.aftas.entities.implementations.CompetitionMember;

@Data
public class RankingResponse {

    private int rank;
    private int score;
    private Member member;
    private Competition competition;
}
